package shop.bookbom.shop.domain.cartitem.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class CartItemNotFoundException extends BaseException {
    public CartItemNotFoundException() {
        super(ErrorCode.CARTITEM_NOT_FOUND);
    }

    public CartItemNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CartItemNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
