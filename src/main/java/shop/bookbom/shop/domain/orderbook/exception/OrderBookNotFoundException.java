package shop.bookbom.shop.domain.orderbook.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class OrderBookNotFoundException extends BaseException {
    public OrderBookNotFoundException() {
        super(ErrorCode.ORDERBOOK_NOT_FOUND);
    }
}
