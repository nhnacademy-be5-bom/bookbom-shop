package shop.bookbom.shop.domain.payment.service;

import shop.bookbom.shop.domain.payment.dto.PaymentRequest;
import shop.bookbom.shop.domain.payment.dto.PaymentSuccessResponse;

public interface PaymentService {
    public PaymentSuccessResponse getPaymnetConfirm(PaymentRequest paymentRequest);

}
