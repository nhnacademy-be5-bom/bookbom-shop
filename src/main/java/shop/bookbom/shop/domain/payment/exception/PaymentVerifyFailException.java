package shop.bookbom.shop.domain.payment.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class PaymentVerifyFailException extends BaseException {
    public PaymentVerifyFailException() {
        super(ErrorCode.PAYMENT_VERIFY_FAIL);
    }
}
