package shop.bookbom.shop.domain.payment.service;

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
import shop.bookbom.shop.domain.order.entity.Order;
import shop.bookbom.shop.domain.order.exception.OrderNotFoundException;
import shop.bookbom.shop.domain.order.repository.OrderRepository;
import shop.bookbom.shop.domain.orderbook.dto.OrderBookInfoDto;
import shop.bookbom.shop.domain.orderbook.entity.OrderBook;
import shop.bookbom.shop.domain.orderbook.repository.OrderBookRepository;
import shop.bookbom.shop.domain.orderstatus.exception.OrderStatusNotFoundException;
import shop.bookbom.shop.domain.orderstatus.repository.OrderStatusRepository;
import shop.bookbom.shop.domain.payment.dto.PaymentResponse;
import shop.bookbom.shop.domain.payment.dto.PaymentSuccessResponse;
import shop.bookbom.shop.domain.payment.entity.Payment;
import shop.bookbom.shop.domain.payment.exception.PaymentNotAllowedException;
import shop.bookbom.shop.domain.payment.exception.PaymentVerifyFailException;
import shop.bookbom.shop.domain.payment.repository.PaymentRepository;
import shop.bookbom.shop.domain.paymentmethod.entity.PaymentMethod;
import shop.bookbom.shop.domain.paymentmethod.exception.PaymentMethodNotFoundException;
import shop.bookbom.shop.domain.paymentmethod.repository.PaymentMethodRepository;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final OrderBookRepository orderBookRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final BookRepository bookRepository;
    private final BookFileRepository bookFileRepository;
    private final DeliveryRepository deliveryRepository;

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
        Payment save = paymentRepository.save(payment);
        return save;
    }

    @Transactional
    @Override
    public PaymentSuccessResponse orderComplete(Payment payment) {
        Order order = orderRepository.findById(payment.getOrder().getId())
                .orElseThrow(OrderNotFoundException::new);

        List<OrderBook> orderBooks = orderBookRepository.findByOrder(order);

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

}
