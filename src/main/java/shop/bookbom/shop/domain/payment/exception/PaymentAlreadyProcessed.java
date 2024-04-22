package shop.bookbom.shop.domain.payment.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class PaymentAlreadyProcessed extends BaseException {
    public PaymentAlreadyProcessed() {
        super(ErrorCode.PAYMENT_ALREADY_PROCESSED);
    }
}
