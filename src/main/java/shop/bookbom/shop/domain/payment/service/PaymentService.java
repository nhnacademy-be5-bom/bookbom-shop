package shop.bookbom.shop.domain.payment.service;

import shop.bookbom.shop.domain.order.entity.Order;
import shop.bookbom.shop.domain.ordercoupon.entity.OrderCoupon;
import shop.bookbom.shop.domain.payment.dto.PaymentRequest;
import shop.bookbom.shop.domain.payment.dto.PaymentResponse;
import shop.bookbom.shop.domain.payment.dto.PaymentSuccessResponse;
import shop.bookbom.shop.domain.payment.entity.Payment;

public interface PaymentService {
    public PaymentResponse requestPaymentConfirm(PaymentRequest paymentRequest);

    public Order verifyRequest(String orderId, Integer amount);

    public Payment savePaymentInfo(PaymentResponse paymentResponse, Order order);

    public PaymentSuccessResponse orderComplete(Payment payment);

    public PaymentSuccessResponse getPaymnetConfirm(PaymentRequest paymentRequest);

    public void decreasePoints(Order order);

    public void useCoupon(OrderCoupon orderCoupon);
}
