package shop.bookbom.shop.domain.couponpolicy.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class CouponPolicyInvalidException extends BaseException {
    public CouponPolicyInvalidException() {
        super(ErrorCode.COMMON_INVALID_PARAMETER);
    }
}
