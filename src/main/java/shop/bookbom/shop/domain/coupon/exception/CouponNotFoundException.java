package shop.bookbom.shop.domain.coupon.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class CouponNotFoundException extends BaseException {
    public CouponNotFoundException() {
        super(ErrorCode.COMMON_ENTITY_NOT_FOUND);
    }
}
