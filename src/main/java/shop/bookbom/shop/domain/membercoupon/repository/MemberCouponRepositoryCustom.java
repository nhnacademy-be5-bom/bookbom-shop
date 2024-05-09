package shop.bookbom.shop.domain.membercoupon.repository;

import java.util.List;
import shop.bookbom.shop.domain.membercoupon.dto.MemberCouponDto;

public interface MemberCouponRepositoryCustom {
    List<MemberCouponDto> getAllMemberCoupons(Long memberId);
}
