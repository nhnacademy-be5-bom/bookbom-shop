package shop.bookbom.shop.domain.orderstatus.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class OrderStatusNotFoundException extends BaseException {
    public OrderStatusNotFoundException() {
        super(ErrorCode.ORDER_STATUS_NOT_FOUN);
    }
}
