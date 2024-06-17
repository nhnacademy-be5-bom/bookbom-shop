package shop.bookbom.shop.domain.membercoupon.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class MemberCouponCanNotUse extends BaseException {
    public MemberCouponCanNotUse() {
        super(ErrorCode.MEMBERCOUPON_CANNOT_USE);
    }
}
