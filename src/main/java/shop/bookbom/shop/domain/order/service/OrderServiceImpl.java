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

    /**
     * 주문 전에 bookId로 책 정보를 불러오고 포장지 전체 List를 받아오는 메소드
     *
     * @param beforeOrderRequestList(bookId,quantity)
     * @return 책 제목, 책 이미지, 수량, 가격 그리고 전체 주문 갯수, 포장지 전체 List
     */
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

    /**
     * 포장지 선택 정보를 처리하는 메소드
     *
     * @param
     * @param wrapperSelectRequest(책정보와 선택한 포장지 의 List와 전체 주문 갯수)
     * @return 포장지 선택 request 와 userId를 내보냄
     */
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
        //배송 예정일 리스트 생성
        List<String> estimatedDateList = createEstimatedDateList();

        return WrapperSelectResponse.builder()
                .totalOrderCount(totalOrderCount)
                .wrapperSelectResponseList(wrapperSelectBookResponseList)
                .estimatedDateList(estimatedDateList)
                .deliveryCost(5000)
                .wrapCost(wrapCost)
                .build();
    }

    /**
     * 주문 처리
     *
     * @param openOrderRequest
     * @return 주문번호, 주문가격, 주문이름
     */
    @Override
    @Transactional
    public OrderResponse processOpenOrder(OpenOrderRequest openOrderRequest) {
        //비회원 주문자 정보 저장
        User user = saveUserInfo(openOrderRequest);
        //주문 정보 저장
        Order order = saveOrder(openOrderRequest, user);
        //배송 관련 정보 저장
        saveDeliveryInfo(openOrderRequest, order);
        //주문 책 정보 저장
        saveOrderBookInfo(openOrderRequest, order);
        //재고체크 후 재고 감소
        for (WrapperSelectBookRequest bookRequest : openOrderRequest.getWrapperSelectRequestList()) {
            checkStock(bookRequest.getBookId(), bookRequest.getQuantity());
            decreaseStock(bookRequest.getBookId(), bookRequest.getQuantity());
        }

        //응답 반환
        return OrderResponse.builder().orderId(order.getOrderNumber())
                .orderName(order.getOrderInfo())
                .amount(order.getTotalCost())
                .build();
    }

    /**
     * 영어 요일을 한글로 바꿔주는 메소드 ex) monday -> 월
     *
     * @param dayOfWeek
     * @return string
     */
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

    /**
     * 책id와 수량으로 책 정보 찾음
     *
     * @param bookId
     * @param bookQuantity
     * @return
     */
    private BeforeOrderBookResponse getBookDetailInfo(Long bookId, Integer bookQuantity) {
        //bookId로 책 가져옴
        Book book = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);
        //title 가져옴
        String title = book.getTitle();
        //cost 가져옴
        Integer cost = book.getCost();
        //썸네일 찾음
        String imageUrl = bookFileRepository.getBookImageUrl(bookId);
        //할인가 가져옴
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

    /**
     * 재고체크 메소드
     *
     * @param bookId
     * @param quantity
     */
    private void checkStock(Long bookId, Integer quantity) {
        //책id로 책의 재고 가져와서 사려는 수량이 재고보다 크면 exception
        Integer stock = bookRepository.getStockById(bookId);
        if (quantity > stock) {
            throw new LowStockException();
        }
    }

    /**
     * 배송 예정일 리스트 생성
     *
     * @return
     */
    private List<String> createEstimatedDateList() {
        List<String> estimatedDateList = new ArrayList<>();
        int daysToAdd = 1;
        while (estimatedDateList.size() < 5) {
            //오늘 날짜를 불러와서 내일부터 배송가능 날짜 5개를 받음
            LocalDate localDate = LocalDate.now().plusDays(daysToAdd);
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();
            //토, 일 제외
            if (!(dayOfWeek.equals(DayOfWeek.SATURDAY) ||
                    dayOfWeek.equals(DayOfWeek.SUNDAY))) {
                // ex) 금(5/3) 형식
                String dateString = localDate.format(DateTimeFormatter.ofPattern("M/d"));
                String dayofWeekKorean = getDayofWeekKorean(dayOfWeek);
                String estimatedDate = dayofWeekKorean + "(" + dateString + ")";

                estimatedDateList.add(estimatedDate);
            }
            daysToAdd++;
        }
        return estimatedDateList;
    }

    /**
     * 배송정보 저장
     *
     * @param openOrderRequest
     * @param order
     */
    private void saveDeliveryInfo(OpenOrderRequest openOrderRequest, Order order) {
        //delivery address 빌더
        DeliveryAddress deliveryAddress = DeliveryAddress.builder().zipCode(openOrderRequest.getZipCode())
                .deliveryAddress(openOrderRequest.getDeliveryAddress())
                .addressDetail(openOrderRequest.getAddressDetail())
                .build();
        // 저장
        deliveryAddressRepository.save(deliveryAddress);

        //string의 배송예정일을 localdate로 변환
        String estimatedDateToString = openOrderRequest.getEstimatedDateTostring();
        String[] parts = estimatedDateToString.split("[()]");
        String[] dateParts = parts[1].split("/");

        // 월과 일 추출
        int month = Integer.parseInt(dateParts[0]);
        int day = Integer.parseInt(dateParts[1]);

        // LocalDate 생성
        LocalDate estimatedDate = LocalDate.of(LocalDate.now().getYear(), Month.of(month), day);
        //delivery 빌더
        Delivery delivery = Delivery.builder().order(order)
                .name(openOrderRequest.getName())
                .phoneNumber(openOrderRequest.getPhoneNumber())
                .cost(openOrderRequest.getDeliveryCost())
                .estimatedDate(estimatedDate)
                .deliveryAddress(deliveryAddress)
                .build();
        //저장
        deliveryRepository.save(delivery);
    }

    /**
     * 주문 정보 저장
     *
     * @param openOrderRequest
     * @param user
     * @return order
     */
    private Order saveOrder(OpenOrderRequest openOrderRequest, User user) {
        //uuid로 랜덤 32자리 주문 번호 생성
        UUID uuid = UUID.randomUUID();
        String orderNumber = uuid.toString().replaceAll("-", "");
        //request의 bookid로 책 찾음
        Book book = bookRepository.findById(openOrderRequest.getWrapperSelectRequestList().get(0).getBookId())
                .orElseThrow(BookNotFoundException::new);
        int totalOrderCount = 0;
        for (WrapperSelectBookRequest bookrequest : openOrderRequest.getWrapperSelectRequestList()) {
            totalOrderCount += bookrequest.getQuantity();
        }
        //주문 이름 생성
        String orderInfo = book.getTitle() + " 외 " + totalOrderCount + "건";
        //주문상태 = "결제전"
        OrderStatus orderStatus = orderStatusRepository.findByName("결제전")
                .orElseThrow(OrderStatusNotFoundException::new);
        //order 빌더
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

    /**
     * 비회원 정보 저장
     *
     * @param openOrderRequest
     * @return
     */
    private User saveUserInfo(OpenOrderRequest openOrderRequest) {
        Role role = roleRepository.findByName("비회원")
                .orElseThrow(RoleNotFoundException::new);
        //user 빌더
        User user = User.builder().email(openOrderRequest.getEmail())
                .password(openOrderRequest.getPassword())
                .registered(false)
                .role(role)
                .build();
        userRepository.save(user);
        return user;
    }

    /**
     * 주문책 저장
     *
     * @param openOrderRequest
     * @param order
     */
    private void saveOrderBookInfo(OpenOrderRequest openOrderRequest, Order order) {

        for (WrapperSelectBookRequest bookRequest : openOrderRequest.getWrapperSelectRequestList()) {
            Book book = bookRepository.findById(bookRequest.getBookId())
                    .orElseThrow(BookNotFoundException::new);
            Wrapper wrapper = wrapperRepository.findByName(bookRequest.getWrapperName())
                    .orElseThrow(WrapperNotFoundException::new);

            boolean packaging = true;
            //포장지 이름이 "안함"이면 packaging이 false
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

    /**
     * 재고 감소 메소드
     *
     * @param bookId
     * @param quantity
     */
    public void decreaseStock(Long bookId, Integer quantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);
        //재고 감소
        int NowStock = book.getStock() - quantity;
        //재고가 마이너스가 되면 exception
        if (NowStock < 0) {
            throw new LowStockException();
        }
        book.updateStock(NowStock);

        bookRepository.save(book);
    }


}

