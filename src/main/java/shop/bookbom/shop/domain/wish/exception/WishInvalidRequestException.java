package shop.bookbom.shop.domain.wish.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class WishInvalidRequestException extends BaseException {
    public WishInvalidRequestException(ErrorCode errorCode) {
        super(errorCode);
    }

    public WishInvalidRequestException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public WishInvalidRequestException() {
        super(ErrorCode.COMMON_INVALID_PARAMETER);
    }
}
