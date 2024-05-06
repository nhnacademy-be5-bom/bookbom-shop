package shop.bookbom.shop.domain.delivery.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class DeliveryNotFoundException extends BaseException {
    public DeliveryNotFoundException() {
        super(ErrorCode.DELIVERY_NOT_FOUNT);
    }
}
