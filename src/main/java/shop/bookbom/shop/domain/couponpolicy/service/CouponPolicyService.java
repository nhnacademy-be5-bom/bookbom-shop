package shop.bookbom.shop.domain.couponpolicy.service;

import java.util.List;
import shop.bookbom.shop.domain.couponpolicy.dto.request.CouponPolicyAddRequest;
import shop.bookbom.shop.domain.couponpolicy.dto.request.CouponPolicyDeleteRequest;
import shop.bookbom.shop.domain.couponpolicy.dto.CouponPolicyInfoDto;

public interface CouponPolicyService {
    void addCouponPolicy(CouponPolicyAddRequest request);

    void deleteCouponPolicy(CouponPolicyDeleteRequest request);

    void updateCouponPolicy(CouponPolicyInfoDto request);

    List<CouponPolicyInfoDto> getCouponPolicyInfo();
}
