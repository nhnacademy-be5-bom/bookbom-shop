package shop.bookbom.shop.domain.membercoupon.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class MemberCouponNotFoundException extends BaseException {
    public MemberCouponNotFoundException() {
        super(ErrorCode.MEMBERCOUPON_NOT_FOUND);
    }
}
