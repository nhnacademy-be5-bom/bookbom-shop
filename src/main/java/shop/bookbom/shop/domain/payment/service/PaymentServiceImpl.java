package shop.bookbom.shop.domain.payment.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import shop.bookbom.shop.domain.payment.adapter.PaymentAdapter;
import shop.bookbom.shop.domain.payment.dto.PaymentRequest;
import shop.bookbom.shop.domain.payment.dto.PaymentResponse;
import shop.bookbom.shop.domain.payment.dto.PaymentSuccessResponse;
import shop.bookbom.shop.domain.payment.entity.Payment;
import shop.bookbom.shop.domain.payment.exception.PaymentNotAllowedException;
import shop.bookbom.shop.domain.payment.exception.PaymentVerifyFailException;
import shop.bookbom.shop.domain.payment.repository.PaymentRepository;
import shop.bookbom.shop.domain.paymentmethod.entity.PaymentMethod;
import shop.bookbom.shop.domain.paymentmethod.exception.PaymentMethodNotFoundException;
import shop.bookbom.shop.domain.paymentmethod.repository.PaymentMethodRepository;
import shop.bookbom.shop.domain.pointhistory.entity.ChangeReason;
import shop.bookbom.shop.domain.pointhistory.entity.PointHistory;
import shop.bookbom.shop.domain.pointhistory.repository.PointHistoryRepository;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentAdapter paymentAdapter;
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

    /**
     * 토스페이 결제 승인 요청 보낸 후 받은 데이터로 결제 완료 응답 구성
     *
     * @param paymentRequest
     * @return
     */
    @Transactional
    @Override
    public PaymentSuccessResponse getPaymnetConfirm(PaymentRequest paymentRequest) {
        //토스페이에 결제 승인 요청 보냄
        PaymentResponse paymentResponse = paymentAdapter.requestPaymentConfirm(paymentRequest);
        //받은 데이터 중 결제 금액이 order의 결제금액과 같은지 비교 검증
        Order order = verifyRequest(paymentResponse.getOrderId(), paymentResponse.getTotalAmount());
        //payment 저장
        Payment payment = savePaymentInfo(paymentResponse, order);
        //회원이면
        if (order.getUser().isRegistered()) {
            //포인트 감소
            if (order.getUsedPoint() != 0) {
                decreasePoints(order);
            }
            //쿠폰 사용
            if (orderCouponRepository.existsByOrder(order)) {
                OrderCoupon orderCoupon = orderCouponRepository.findByOrder(order)
                        .orElseThrow(OrderCouponNotFoundException::new);
                useCoupon(orderCoupon);

            }
        }
        return orderComplete(payment);
    }

    /**
     * 결제 검증
     *
     * @param orderId
     * @param amount
     * @return
     */
    private Order verifyRequest(String orderId, Integer amount) {
        Order order = orderRepository.findByOrderNumber(orderId)
                .orElseThrow(OrderNotFoundException::new);

        if (!order.getTotalCost().equals(amount)) {
            throw new PaymentVerifyFailException();
        }
        return order;

    }

    /**
     * 결제 정보 저장
     *
     * @param paymentResponse
     * @param order
     * @return
     */
    private Payment savePaymentInfo(PaymentResponse paymentResponse, Order order) {
        //결제 정보의 결제 수단에 따라 payment에 결제수단 저장
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
        //주문시간
        LocalDateTime localDateTime =
                OffsetDateTime.parse(paymentResponse.getApprovedAt(), DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                        .toLocalDateTime();
        order.updateOrderDate(localDateTime);
        //주문 상태를 대기로 바꿈
        order.updateStatus(orderStatusRepository.findByName("대기")
                .orElseThrow(OrderStatusNotFoundException::new));

        Payment payment = Payment.builder().order(order).cost(paymentResponse.getTotalAmount())
                .key(paymentResponse.getPaymentKey())
                .paymentMethod(paymentMethod).build();
        return paymentRepository.save(payment);
    }

    /**
     * payment로 주문 완료 페이지에 불러올 데이터 찾고 반환
     *
     * @param payment
     * @return
     */
    private PaymentSuccessResponse orderComplete(Payment payment) {
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

    /**
     * 포인트 감소 메소드
     *
     * @param order
     */
    private void decreasePoints(Order order) {
        Member member = memberRepository.findById(order.getUser().getId())
                .orElseThrow(MemberNotFoundException::new);

        member.updatePoints(member.getPoint() - order.getUsedPoint());
        PointHistory pointHistory = PointHistory.builder()
                .changePoint(order.getUsedPoint())
                .changeReason(ChangeReason.USE)
                .changeDate(LocalDateTime.now())
                .member(member).build();
        pointHistoryRepository.save(pointHistory);
    }

    /**
     * 쿠폰 사용 메소드
     *
     * @param orderCoupon
     */
    private void useCoupon(OrderCoupon orderCoupon) {
        MemberCoupon memberCoupon = memberCouponRepository.findByCoupon(orderCoupon.getCoupon())
                .orElseThrow(MemberCouponNotFoundException::new);
        if (!memberCoupon.getStatus().getValue().equals("NEW")) {
            throw new MemberCouponCanNotUse();
        }
        memberCoupon.updateUseDate(LocalDate.now());
        memberCoupon.updateCouponStatus(CouponStatus.USED);
    }


}
