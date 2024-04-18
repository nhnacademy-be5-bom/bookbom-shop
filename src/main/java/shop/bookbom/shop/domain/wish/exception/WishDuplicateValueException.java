package shop.bookbom.shop.domain.wish.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class WishDuplicateValueException extends BaseException {
    public WishDuplicateValueException(ErrorCode errorCode) {
        super(errorCode);
    }

    public WishDuplicateValueException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public WishDuplicateValueException() {
        super(ErrorCode.WISH_DUPLICATE_VALUE);
    }
}
