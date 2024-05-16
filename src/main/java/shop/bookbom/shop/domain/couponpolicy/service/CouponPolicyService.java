package shop.bookbom.shop.domain.couponpolicy.service;

import java.util.List;
import shop.bookbom.shop.domain.couponpolicy.dto.request.CouponPolicyAddRequest;
import shop.bookbom.shop.domain.couponpolicy.dto.request.CouponPolicyDeleteRequest;
import shop.bookbom.shop.domain.couponpolicy.dto.CouponPolicyInfoDto;

public interface CouponPolicyService {
    void addCouponPolicy(CouponPolicyAddRequest request, Long userId);

    void deleteCouponPolicy(CouponPolicyDeleteRequest request, Long userId);

    void updateCouponPolicy(CouponPolicyInfoDto request, Long userId);

    List<CouponPolicyInfoDto> getCouponPolicyInfo(Long userId);
}
