package shop.bookbom.shop.domain.payment.service;

import shop.bookbom.shop.domain.payment.dto.request.PaymentRequest;
import shop.bookbom.shop.domain.payment.dto.response.OrderIdResponse;
import shop.bookbom.shop.domain.payment.dto.response.PaymentSuccessResponse;

public interface PaymentService {
    OrderIdResponse getPaymnetConfirm(PaymentRequest paymentRequest);

    PaymentSuccessResponse orderComplete(Long orderId);

}
