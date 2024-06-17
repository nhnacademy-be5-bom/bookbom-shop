package shop.bookbom.shop.domain.membercoupon.repository;

import java.util.List;
import shop.bookbom.shop.domain.coupon.entity.Coupon;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.membercoupon.dto.MemberCouponDto;
import shop.bookbom.shop.domain.membercoupon.entity.MemberCoupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.bookbom.shop.domain.coupon.dto.response.MyCouponInfoResponse;
import shop.bookbom.shop.domain.coupon.dto.response.MyCouponRecordRepoDto;
import shop.bookbom.shop.domain.coupon.dto.response.MyCouponRecordResponse;

public interface MemberCouponRepositoryCustom {
    List<MemberCouponDto> getAllMemberCoupons(Long memberId);

    MemberCoupon findByCouponAndMember(Coupon coupon, Member member);

    Page<MyCouponInfoResponse> getMyCouponInfo(Pageable pageable, Long userId);

    List<MyCouponRecordRepoDto> getMyCouponRecordList(Long userId);

    Page<MyCouponRecordResponse> getMyCouponRecordPage(Pageable pageable, List<MyCouponRecordResponse> recordList, Long total);
}
