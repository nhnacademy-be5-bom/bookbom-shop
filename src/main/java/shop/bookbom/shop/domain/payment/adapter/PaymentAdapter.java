package shop.bookbom.shop.domain.payment.adapter;

import shop.bookbom.shop.domain.payment.dto.request.PaymentRequest;
import shop.bookbom.shop.domain.payment.dto.response.PaymentResponse;

public interface PaymentAdapter {
    PaymentResponse requestPaymentConfirm(PaymentRequest paymentRequest);
}
