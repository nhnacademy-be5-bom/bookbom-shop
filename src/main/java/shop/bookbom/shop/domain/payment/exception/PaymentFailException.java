package shop.bookbom.shop.domain.payment.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class PaymentFailException extends BaseException {
    public PaymentFailException() {
        super(ErrorCode.PAYMENT_FAILED);
    }

    @Override
    public ErrorCode getErrorCode() {
        return super.getErrorCode();
    }

    public PaymentFailException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
