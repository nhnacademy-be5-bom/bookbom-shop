package shop.bookbom.shop.domain.membercoupon.repository;

import java.util.List;
import shop.bookbom.shop.domain.coupon.entity.Coupon;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.membercoupon.dto.MemberCouponDto;
import shop.bookbom.shop.domain.membercoupon.entity.MemberCoupon;

public interface MemberCouponRepositoryCustom {
    List<MemberCouponDto> getAllMemberCoupons(Long memberId);

    MemberCoupon findByCouponAndMember(Coupon coupon, Member member);
}
