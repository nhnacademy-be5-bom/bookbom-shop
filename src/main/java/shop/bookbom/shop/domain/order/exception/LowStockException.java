package shop.bookbom.shop.domain.order.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class LowStockException extends BaseException {
    public LowStockException() {
        super(ErrorCode.LOW_STOCK);
    }

    public LowStockException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
