package shop.bookbom.shop.domain.couponpolicy.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class CouponPolicyNotFoundException extends BaseException {
    public CouponPolicyNotFoundException() {
        super(ErrorCode.COMMON_ENTITY_NOT_FOUND);
    }
}
