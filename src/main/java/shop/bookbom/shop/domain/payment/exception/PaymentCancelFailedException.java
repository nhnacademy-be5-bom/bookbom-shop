package shop.bookbom.shop.domain.payment.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class PaymentCancelFailedException extends BaseException {
    public PaymentCancelFailedException() {
        super(ErrorCode.PAYMENT_CANCEL_FAILED);
    }
}
