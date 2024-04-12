package shop.bookbom.shop.domain.cartitem.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class CartItemNotFoundException extends BaseException {
    public CartItemNotFoundException() {
        super(ErrorCode.CART_ITEM_NOT_FOUND);
    }
}
