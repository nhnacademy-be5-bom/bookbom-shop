package shop.bookbom.shop.domain.payment.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class PaymentNotAllowedException extends BaseException {
    public PaymentNotAllowedException() {
        super(ErrorCode.PAYMENT_NOT_ALLOWED);
    }
}
