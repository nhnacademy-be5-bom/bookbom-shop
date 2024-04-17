package shop.bookbom.shop.domain.payment.service;

import shop.bookbom.shop.domain.order.entity.Order;
import shop.bookbom.shop.domain.payment.dto.PaymentResponse;
import shop.bookbom.shop.domain.payment.dto.PaymentSuccessResponse;
import shop.bookbom.shop.domain.payment.entity.Payment;

public interface PaymentService {

    public Order verifyRequest(String orderId, Integer amount);

    public Payment savePaymentInfo(PaymentResponse paymentResponse, Order order);

    public PaymentSuccessResponse orderComplete(Payment payment);
}
