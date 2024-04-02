package shop.bookbom.shop.domain.cart.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class CartInvalidAddRequestException extends BaseException {
    public CartInvalidAddRequestException() {
        super(ErrorCode.CART_INVALID_ADD_REQUEST);
    }

    public CartInvalidAddRequestException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CartInvalidAddRequestException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
