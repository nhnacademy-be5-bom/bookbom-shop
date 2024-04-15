package shop.bookbom.shop.domain.order.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class OrderInfoInvalidException extends BaseException {
    public OrderInfoInvalidException() {
        super(ErrorCode.COMMON_INVALID_PARAMETER);

    }
    public OrderInfoInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }

    public OrderInfoInvalidException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
