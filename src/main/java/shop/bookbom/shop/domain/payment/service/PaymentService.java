package shop.bookbom.shop.domain.payment.service;

import shop.bookbom.shop.domain.order.entity.Order;
import shop.bookbom.shop.domain.payment.dto.request.PaymentRequest;
import shop.bookbom.shop.domain.payment.dto.response.PaymentSuccessResponse;

public interface PaymentService {
    Order getPaymnetConfirm(PaymentRequest paymentRequest);

    PaymentSuccessResponse orderComplete(Long orderId);

    PaymentSuccessResponse orderFreeComplete(Long orderId);

}
