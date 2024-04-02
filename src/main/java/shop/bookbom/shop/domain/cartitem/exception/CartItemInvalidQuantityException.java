package shop.bookbom.shop.domain.cartitem.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class CartItemInvalidQuantityException extends BaseException {
    public CartItemInvalidQuantityException() {
        super(ErrorCode.CART_ITEM_INVALID_QUANTITY);
    }
}
