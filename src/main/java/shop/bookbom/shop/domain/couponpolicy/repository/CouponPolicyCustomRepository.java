package shop.bookbom.shop.domain.couponpolicy.repository;

import shop.bookbom.shop.domain.couponpolicy.dto.CouponPolicyInfoDto;

public interface CouponPolicyCustomRepository {
    long updateCouponPolicyInfo(CouponPolicyInfoDto couponPolicyInfoDto);
}
