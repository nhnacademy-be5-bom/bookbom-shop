package shop.bookbom.shop.domain.wish.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class WishNotFoundException extends BaseException {
    public WishNotFoundException() {
        super(ErrorCode.WISH_NOT_FOUND);
    }

    public WishNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public WishNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}

