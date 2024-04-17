package shop.bookbom.shop.domain.order.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class OrderNotFoundException extends BaseException {
    public OrderNotFoundException() {
        super(ErrorCode.ORDER_NOT_FOUNT);
    }
}
