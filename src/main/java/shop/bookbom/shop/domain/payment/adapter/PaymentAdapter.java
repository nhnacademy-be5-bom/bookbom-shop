package shop.bookbom.shop.domain.payment.adapter;

import shop.bookbom.shop.domain.payment.dto.PaymentRequest;
import shop.bookbom.shop.domain.payment.dto.PaymentResponse;

public interface PaymentAdapter {
    PaymentResponse requestPaymentConfirm(PaymentRequest paymentRequest);
}
