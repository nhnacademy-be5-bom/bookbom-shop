package shop.bookbom.shop.domain.order.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.exception.BookNotFoundException;
import shop.bookbom.shop.domain.book.repository.BookRepository;
import shop.bookbom.shop.domain.bookfile.repository.BookFileRepository;
import shop.bookbom.shop.domain.delivery.entity.Delivery;
import shop.bookbom.shop.domain.delivery.repository.DeliveryRepository;
import shop.bookbom.shop.domain.deliveryaddress.entity.DeliveryAddress;
import shop.bookbom.shop.domain.deliveryaddress.repository.DeliveryAddressRepository;
import shop.bookbom.shop.domain.order.dto.request.BeforeOrderRequest;
import shop.bookbom.shop.domain.order.dto.request.BeforeOrderRequestList;
import shop.bookbom.shop.domain.order.dto.request.OpenOrderRequest;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectBookRequest;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectRequest;
import shop.bookbom.shop.domain.order.dto.response.BeforeOrderBookResponse;
import shop.bookbom.shop.domain.order.dto.response.BeforeOrderResponse;
import shop.bookbom.shop.domain.order.dto.response.OrderResponse;
import shop.bookbom.shop.domain.order.dto.response.WrapperSelectBookResponse;
import shop.bookbom.shop.domain.order.dto.response.WrapperSelectResponse;
import shop.bookbom.shop.domain.order.entity.Order;
import shop.bookbom.shop.domain.order.exception.LowStockException;
import shop.bookbom.shop.domain.order.repository.OrderRepository;
import shop.bookbom.shop.domain.orderbook.entity.OrderBook;
import shop.bookbom.shop.domain.orderbook.entity.OrderBookStatus;
import shop.bookbom.shop.domain.orderbook.repository.OrderBookRepository;
import shop.bookbom.shop.domain.orderstatus.entity.OrderStatus;
import shop.bookbom.shop.domain.orderstatus.exception.OrderStatusNotFoundException;
import shop.bookbom.shop.domain.orderstatus.repository.OrderStatusRepository;
import shop.bookbom.shop.domain.role.entity.Role;
import shop.bookbom.shop.domain.role.repository.RoleRepository;
import shop.bookbom.shop.domain.users.entity.User;
import shop.bookbom.shop.domain.users.exception.RoleNotFoundException;
import shop.bookbom.shop.domain.users.repository.UserRepository;
import shop.bookbom.shop.domain.wrapper.dto.WrapperDto;
import shop.bookbom.shop.domain.wrapper.entity.Wrapper;
import shop.bookbom.shop.domain.wrapper.exception.WrapperNotFoundException;
import shop.bookbom.shop.domain.wrapper.repository.WrapperRepository;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final BookRepository bookRepository;
    private final BookFileRepository bookFileRepository;
    private final WrapperRepository wrapperRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final OrderBookRepository orderBookRepository;
    private final EntityManager em;


    @Override
    @Transactional(readOnly = true)
    public BeforeOrderResponse getOrderBookInfo(BeforeOrderRequestList beforeOrderRequestList) {
        int totalOrderCount = 0;
        List<BeforeOrderBookResponse> beforeOrderBookResponseList = new ArrayList<>();
        //각 책에 대한 정보를 list로부터 가져와서 foreach문 돌림
        for (BeforeOrderRequest bookRequest : beforeOrderRequestList.getBeforeOrderRequests()) {

            BeforeOrderBookResponse beforeOrderBookResponse =
                    getBookDetailInfo(bookRequest.getBookId(), bookRequest.getQuantity());
            beforeOrderBookResponseList.add(beforeOrderBookResponse);
            //총 주문 개수 다 더함
            totalOrderCount += bookRequest.getQuantity();
            checkStock(bookRequest.getBookId(), bookRequest.getQuantity());
        }
        //모든 포장지 list 가져옴
        List<Wrapper> wrapperList = wrapperRepository.findAll();
        List<WrapperDto> wrapperDtoList = new ArrayList<>();
        for (Wrapper wrapper : wrapperList) {
            WrapperDto wrapperDto =
                    new WrapperDto(wrapper.getId(), wrapper.getName(), wrapper.getCost(), wrapper.getFile().getUrl());
            wrapperDtoList.add(wrapperDto);

        }


        //주문 응답 객체 생성 후 정보 저장
        return BeforeOrderResponse.builder()
                .beforeOrderBookResponseList(beforeOrderBookResponseList)
                .totalOrderCount(totalOrderCount)
                .wrapperList(wrapperDtoList)
                .build();

    }

    @Override
    @Transactional
    public WrapperSelectResponse selectWrapper(WrapperSelectRequest wrapperSelectRequest) {
        int totalOrderCount = 0;
        int wrapCost = 0;
        //request을 가져와서 총 주문 갯수와 포장지 선택 리스트를 받아옴
        //포장지 셀렉 응답 리스트를 만듬
        List<WrapperSelectBookResponse> wrapperSelectBookResponseList = new ArrayList<>();
        for (WrapperSelectBookRequest selectBookRequest : wrapperSelectRequest.getWrapperSelectBookRequestList()) {
            BeforeOrderBookResponse bookDetailInfo =
                    getBookDetailInfo(selectBookRequest.getBookId(), selectBookRequest.getQuantity());
            WrapperSelectBookResponse wrapperSelectBookResponse =
                    WrapperSelectBookResponse.builder()
                            .bookId(bookDetailInfo.getBookId())
                            .bookTitle(bookDetailInfo.getTitle())
                            .cost(bookDetailInfo.getCost())
                            .discountCost(bookDetailInfo.getDiscountCost())
                            .imgUrl(bookDetailInfo.getImageUrl())
                            .quantity(bookDetailInfo.getQuantity())
                            .wrapperName(selectBookRequest.getWrapperName())
                            .build();

            wrapperSelectBookResponseList.add(wrapperSelectBookResponse);
            totalOrderCount += bookDetailInfo.getQuantity();
            Integer costByName = wrapperRepository.getCostByName(selectBookRequest.getWrapperName());
            wrapCost += costByName * bookDetailInfo.getQuantity();

        }
        List<String> estimatedDateList = createEstimatedDateList();

        return WrapperSelectResponse.builder()
                .totalOrderCount(totalOrderCount)
                .wrapperSelectResponseList(wrapperSelectBookResponseList)
                .estimatedDateList(estimatedDateList)
                .deliveryCost(5000)
                .wrapCost(wrapCost)
                .build();
    }

    @Override
    @Transactional
    public OrderResponse processOpenOrder(OpenOrderRequest openOrderRequest) {
        User user = saveUserInfo(openOrderRequest);
        Order order = saveOrder(openOrderRequest, user);
        saveDeliveryInfo(openOrderRequest, order);
        saveOrderBookInfo(openOrderRequest, order);
        //재고체크
        for (WrapperSelectBookRequest bookRequest : openOrderRequest.getWrapperSelectRequestList()) {
            checkStock(bookRequest.getBookId(), bookRequest.getQuantity());
        }


        return OrderResponse.builder().orderId(order.getOrderNumber())
                .orderName(order.getOrderInfo())
                .amount(order.getTotalCost())
                .build();
    }

    private static String getDayofWeekKorean(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return "월";
            case TUESDAY:
                return "화";
            case WEDNESDAY:
                return "수";
            case THURSDAY:
                return "목";
            case FRIDAY:
                return "금";
            default:
                return null;
        }

    }

    private BeforeOrderBookResponse getBookDetailInfo(Long bookId, Integer bookQuantity) {
        //bookId로 책 가져옴
        Book book = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);
        String title = book.getTitle();
        Integer cost = book.getCost();
        String imageUrl = bookFileRepository.getBookImageUrl(bookId);
        Integer discountCost = book.getDiscountCost();


        //새로운 주문 도서 응답 빌더 생성
        return BeforeOrderBookResponse.builder()
                .bookId(bookId)
                .title(title)
                .imageUrl(imageUrl)
                .cost(cost)
                .discountCost(discountCost)
                .quantity(bookQuantity)
                .build();
    }

    private void checkStock(Long bookId, Integer quantity) {
        Integer stock = bookRepository.getStockById(bookId);
        if (quantity > stock) {
            throw new LowStockException();
        }
    }

    private List<String> createEstimatedDateList() {
        List<String> estimatedDateList = new ArrayList<>();
        int daysToAdd = 1;
        while (estimatedDateList.size() < 5) {
            LocalDate localDate = LocalDate.now().plusDays(daysToAdd);
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();
            if (!(dayOfWeek.equals(DayOfWeek.SATURDAY) ||
                    dayOfWeek.equals(DayOfWeek.SUNDAY))) {
                String dateString = localDate.format(DateTimeFormatter.ofPattern("M/d"));
                String dayofWeekKorean = getDayofWeekKorean(dayOfWeek);
                String estimatedDate = dayofWeekKorean + "(" + dateString + ")";

                estimatedDateList.add(estimatedDate);
            }
            daysToAdd++;
        }
        return estimatedDateList;
    }


    private void saveDeliveryInfo(OpenOrderRequest openOrderRequest, Order order) {

        DeliveryAddress deliveryAddress = DeliveryAddress.builder().zipCode(openOrderRequest.getZipCode())
                .deliveryAddress(openOrderRequest.getDeliveryAddress())
                .addressDetail(openOrderRequest.getAddressDetail())
                .build();

        deliveryAddressRepository.save(deliveryAddress);


        String estimatedDateToString = openOrderRequest.getEstimatedDateTostring();
        String[] parts = estimatedDateToString.split("[()]");
        String[] dateParts = parts[1].split("/");

        // 월과 일 추출
        int month = Integer.parseInt(dateParts[0]);
        int day = Integer.parseInt(dateParts[1]);

        // LocalDate 생성
        LocalDate estimatedDate = LocalDate.of(LocalDate.now().getYear(), Month.of(month), day);
//        LocalDate estimatedDate = LocalDate.parse(estimatedDateToString);
        Delivery delivery = Delivery.builder().order(order)
                .name(openOrderRequest.getName())
                .phoneNumber(openOrderRequest.getPhoneNumber())
                .cost(openOrderRequest.getDeliveryCost())
                .estimatedDate(estimatedDate)
                .deliveryAddress(deliveryAddress)
                .build();

        deliveryRepository.save(delivery);
    }


    private Order saveOrder(OpenOrderRequest openOrderRequest, User user) {
        UUID uuid = UUID.randomUUID();
        // UUID를 문자열로 변환
        String orderNumber = uuid.toString().replaceAll("-", "");

        Book book = bookRepository.findById(openOrderRequest.getWrapperSelectRequestList().get(0).getBookId())
                .orElseThrow(BookNotFoundException::new);
        int totalOrderCount = 0;
        for (WrapperSelectBookRequest bookrequest : openOrderRequest.getWrapperSelectRequestList()) {
            totalOrderCount += bookrequest.getQuantity();
        }
        String orderInfo = book.getTitle() + " 외 " + totalOrderCount + "건";

        OrderStatus orderStatus = orderStatusRepository.findByName("결제전")
                .orElseThrow(OrderStatusNotFoundException::new);
        Order order = Order.builder().orderNumber(orderNumber)
                .orderInfo(orderInfo)
                .orderDate(LocalDateTime.now())
                .senderName(openOrderRequest.getName())
                .senderPhoneNumber(openOrderRequest.getPhoneNumber())
                .totalCost(openOrderRequest.getTotalCost())
                .discountCost(openOrderRequest.getDiscountCost())
                .usedPoint(0)
                .user(user)
                .status(orderStatus)
                .build();
        return order;

    }

    private User saveUserInfo(OpenOrderRequest openOrderRequest) {
        Role role = roleRepository.findByName("비회원")
                .orElseThrow(RoleNotFoundException::new);
        User user = User.builder().email(openOrderRequest.getEmail())
                .password(openOrderRequest.getPassword())
                .registered(false)
                .role(role)
                .build();
        userRepository.save(user);
        return user;
    }

    private void saveOrderBookInfo(OpenOrderRequest openOrderRequest, Order order) {

        for (WrapperSelectBookRequest bookRequest : openOrderRequest.getWrapperSelectRequestList()) {
            Book book = bookRepository.findById(bookRequest.getBookId())
                    .orElseThrow(BookNotFoundException::new);
            Wrapper wrapper = wrapperRepository.findByName(bookRequest.getWrapperName())
                    .orElseThrow(WrapperNotFoundException::new);

            boolean packaging = true;
            if (bookRequest.getWrapperName().equals("안함")) {
                packaging = false;
            }
            OrderBook orderBook = OrderBook.builder().quantity(bookRequest.getQuantity())
                    .packaging(packaging)
                    .status(OrderBookStatus.NONE)
                    .book(book)
                    .order(order)
                    .wrapper(wrapper)
                    .build();

            orderBookRepository.save(orderBook);

        }
    }


    public void decreaseStock(Long bookId, Integer quantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);
        book.updateStock(book.getStock() - quantity);
        bookRepository.save(book);
    }


}

