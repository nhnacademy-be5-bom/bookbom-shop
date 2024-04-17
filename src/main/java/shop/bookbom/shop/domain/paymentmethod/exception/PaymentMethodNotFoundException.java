package shop.bookbom.shop.domain.paymentmethod.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class PaymentMethodNotFoundException extends BaseException {
    public PaymentMethodNotFoundException() {
        super(ErrorCode.PAYMENTMETHOD_NOT_FOUND);
    }
}
