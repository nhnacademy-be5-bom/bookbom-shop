package shop.bookbom.shop.domain.payment.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import shop.bookbom.shop.common.exception.ErrorCode;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.exception.BookNotFoundException;
import shop.bookbom.shop.domain.book.repository.BookRepository;
import shop.bookbom.shop.domain.bookfile.repository.BookFileRepository;
import shop.bookbom.shop.domain.delivery.entity.Delivery;
import shop.bookbom.shop.domain.delivery.exception.DeliveryNotFoundException;
import shop.bookbom.shop.domain.delivery.repository.DeliveryRepository;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.exception.MemberNotFoundException;
import shop.bookbom.shop.domain.member.repository.MemberRepository;
import shop.bookbom.shop.domain.membercoupon.entity.CouponStatus;
import shop.bookbom.shop.domain.membercoupon.entity.MemberCoupon;
import shop.bookbom.shop.domain.membercoupon.exception.MemberCouponCanNotUse;
import shop.bookbom.shop.domain.membercoupon.exception.MemberCouponNotFoundException;
import shop.bookbom.shop.domain.membercoupon.repository.MemberCouponRepository;
import shop.bookbom.shop.domain.order.entity.Order;
import shop.bookbom.shop.domain.order.exception.OrderNotFoundException;
import shop.bookbom.shop.domain.order.repository.OrderRepository;
import shop.bookbom.shop.domain.orderbook.dto.OrderBookInfoDto;
import shop.bookbom.shop.domain.orderbook.entity.OrderBook;
import shop.bookbom.shop.domain.orderbook.exception.OrderBookNotFoundException;
import shop.bookbom.shop.domain.orderbook.repository.OrderBookRepository;
import shop.bookbom.shop.domain.ordercoupon.entity.OrderCoupon;
import shop.bookbom.shop.domain.ordercoupon.exception.OrderCouponNotFoundException;
import shop.bookbom.shop.domain.ordercoupon.repository.OrderCouponRepository;
import shop.bookbom.shop.domain.orderstatus.exception.OrderStatusNotFoundException;
import shop.bookbom.shop.domain.orderstatus.repository.OrderStatusRepository;
import shop.bookbom.shop.domain.payment.config.TossPayConfig;
import shop.bookbom.shop.domain.payment.dto.FailureDto;
import shop.bookbom.shop.domain.payment.dto.PaymentRequest;
import shop.bookbom.shop.domain.payment.dto.PaymentResponse;
import shop.bookbom.shop.domain.payment.dto.PaymentSuccessResponse;
import shop.bookbom.shop.domain.payment.entity.Payment;
import shop.bookbom.shop.domain.payment.exception.PaymentFailException;
import shop.bookbom.shop.domain.payment.exception.PaymentNotAllowedException;
import shop.bookbom.shop.domain.payment.exception.PaymentVerifyFailException;
import shop.bookbom.shop.domain.payment.repository.PaymentRepository;
import shop.bookbom.shop.domain.paymentmethod.entity.PaymentMethod;
import shop.bookbom.shop.domain.paymentmethod.exception.PaymentMethodNotFoundException;
import shop.bookbom.shop.domain.paymentmethod.repository.PaymentMethodRepository;
import shop.bookbom.shop.domain.pointhistory.entity.PointHistory;
import shop.bookbom.shop.domain.pointhistory.repository.PointHistoryRepository;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final TossPayConfig tossPayConfig;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final OrderBookRepository orderBookRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final BookRepository bookRepository;
    private final BookFileRepository bookFileRepository;
    private final DeliveryRepository deliveryRepository;
    private final MemberRepository memberRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final OrderCouponRepository orderCouponRepository;
    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    @Override
    public Order verifyRequest(String orderId, Integer amount) {
        Order order = orderRepository.findByOrderNumber(orderId)
                .orElseThrow(OrderNotFoundException::new);

        if (!order.getTotalCost().equals(amount)) {
            throw new PaymentVerifyFailException();
        }
        return order;

    }

    @Transactional
    @Override
    public PaymentResponse requestPaymentConfirm(PaymentRequest paymentRequest) {
        String authorization = Base64.getEncoder().encodeToString(tossPayConfig.getSecretKey().getBytes());
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + authorization);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentRequest> requestEntity = new HttpEntity<>(paymentRequest, headers);
        ResponseEntity<PaymentResponse> responseEntity = null;

        try {
            responseEntity =
                    restTemplate.exchange(tossPayConfig.getConfirmUrl(),
                            HttpMethod.POST,
                            requestEntity,
                            PaymentResponse.class);
            // 응답의 상태코드 확인 등 추가적인 작업 수행 가능
        } catch (HttpStatusCodeException e) {
            if (responseEntity == null || responseEntity.getBody() == null) {
                // responseEntity가 null이거나 응답 본문이 없는 경우
                throw new PaymentFailException(ErrorCode.PAYMENT_FAILED, "결제 응답이 존재하지 않습니다.");
            } else {
                FailureDto failure = responseEntity.getBody().getFailure();
                if (failure != null) {
                    throw new PaymentFailException(ErrorCode.PAYMENT_FAILED, failure.getMessage());
                } else {
                    throw new PaymentFailException();
                }
            }
        }

        return responseEntity.getBody();
    }

    @Transactional
    @Override
    public Payment savePaymentInfo(PaymentResponse paymentResponse, Order order) {

        PaymentMethod paymentMethod;
        if (paymentResponse.getMethod().equals("카드")) {
            paymentMethod =
                    paymentMethodRepository.findByCardCompanyCode(paymentResponse.getCard().getIssuerCode())
                            .orElseThrow(PaymentMethodNotFoundException::new);
        } else if (paymentResponse.getMethod().equals("간편결제")) {
            paymentMethod = paymentMethodRepository.findByName(paymentResponse.getEasyPay().getProvider())
                    .orElseThrow(PaymentMethodNotFoundException::new);
        } else {
            throw new PaymentNotAllowedException();
        }
        LocalDateTime localDateTime =
                OffsetDateTime.parse(paymentResponse.getApprovedAt(), DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                        .toLocalDateTime();
        order.updateOrderDate(localDateTime);

        order.updateStatus(orderStatusRepository.findByName("대기")
                .orElseThrow(OrderStatusNotFoundException::new));

        Payment payment = Payment.builder().order(order).cost(paymentResponse.getTotalAmount())
                .key(paymentResponse.getPaymentKey())
                .paymentMethod(paymentMethod).build();
        return paymentRepository.save(payment);
    }

    @Transactional
    @Override
    public PaymentSuccessResponse orderComplete(Payment payment) {
        Order order = orderRepository.findById(payment.getOrder().getId())
                .orElseThrow(OrderNotFoundException::new);

        List<OrderBook> orderBooks = orderBookRepository.findByOrder(order);
        if (orderBooks.isEmpty()) {
            throw new OrderBookNotFoundException();
        }

        List<OrderBookInfoDto> orderBookInfoDtoList = new ArrayList<>();
        Integer totalCount = 0;
        for (OrderBook orderBook : orderBooks) {
            Book book = bookRepository.findById(orderBook.getBook().getId())
                    .orElseThrow(BookNotFoundException::new);

            String imgUrl = bookFileRepository.getBookImageUrl(book.getId());
            int quantity = orderBook.getQuantity();
            totalCount += quantity;

            orderBookInfoDtoList.add(OrderBookInfoDto.builder()
                    .title(book.getTitle())
                    .cost(book.getCost())
                    .quantity(quantity)
                    .imgUrl(imgUrl)
                    .build());
        }

        Delivery delivery = deliveryRepository.findById(order.getId())
                .orElseThrow(DeliveryNotFoundException::new);

        return PaymentSuccessResponse.builder()
                .orderNumber(payment.getOrder().getOrderNumber())
                .totalAmount(payment.getCost())
                .paymentMethodName(payment.getPaymentMethod().getName())
                .orderInfo(order.getOrderInfo())
                .orderBookInfoDtoList(orderBookInfoDtoList)
                .totalCount(totalCount)
                .deliveryName(delivery.getName())
                .deliveryPhoneNumber(delivery.getPhoneNumber())
                .zipCode(delivery.getDeliveryAddress().getZipCode())
                .deliveryAddress(delivery.getDeliveryAddress().getDeliveryAddress())
                .addressDetail(delivery.getDeliveryAddress().getAddressDetail())
                .build();

    }

    @Transactional
    @Override
    public void decreasePoints(Order order) {
        Member member = memberRepository.findById(order.getUser().getId())
                .orElseThrow(MemberNotFoundException::new);

        member.updatePoints(member.getPoint() - order.getUsedPoint());
        PointHistory pointHistory = PointHistory.builder()
                .changePoint(order.getUsedPoint())
                .changeReason("결제 시 사용")
                .changeDate(LocalDateTime.now())
                .member(member).build();
        pointHistoryRepository.save(pointHistory);
    }

    @Transactional
    @Override
    public void useCoupon(OrderCoupon orderCoupon) {
        MemberCoupon memberCoupon = memberCouponRepository.findByCoupon(orderCoupon.getCoupon())
                .orElseThrow(MemberCouponNotFoundException::new);
        if (!memberCoupon.getStatus().getValue().equals("NEW")) {
            throw new MemberCouponCanNotUse();
        }
        memberCoupon.updateUseDate(LocalDate.now());
        memberCoupon.updateCouponStatus(CouponStatus.USED);
    }

    @Transactional
    @Override
    public PaymentSuccessResponse getPaymnetConfirm(PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse = requestPaymentConfirm(paymentRequest);
        Order order = verifyRequest(paymentResponse.getOrderId(), paymentResponse.getTotalAmount());
        Payment payment = savePaymentInfo(paymentResponse, order);


        if (order.getUser().isRegistered()) {
            if (order.getUsedPoint() != 0) {
                decreasePoints(order);
            }
            if (orderCouponRepository.existsByOrder(order)) {
                OrderCoupon orderCoupon = orderCouponRepository.findByOrder(order)
                        .orElseThrow(OrderCouponNotFoundException::new);
                useCoupon(orderCoupon);

            }
        }
        return orderComplete(payment);
    }


}
