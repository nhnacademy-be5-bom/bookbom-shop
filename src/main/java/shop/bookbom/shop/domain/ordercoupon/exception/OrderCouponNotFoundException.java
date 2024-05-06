package shop.bookbom.shop.domain.ordercoupon.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class OrderCouponNotFoundException extends BaseException {
    public OrderCouponNotFoundException() {
        super(ErrorCode.ORDERCOUPON_NOT_FOUND);
    }
}
