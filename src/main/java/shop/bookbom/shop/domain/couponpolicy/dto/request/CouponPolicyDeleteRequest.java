package shop.bookbom.shop.domain.couponpolicy.dto.request;

import lombok.Builder;
import lombok.Getter;
import shop.bookbom.shop.domain.couponpolicy.entity.CouponPolicy;

@Getter
public class CouponPolicyDeleteRequest {
    private Long couponPolicyId;

    @Builder
    private CouponPolicyDeleteRequest(Long couponPolicyId) {
        this.couponPolicyId = couponPolicyId;
    }

    public static CouponPolicyDeleteRequest of(Long couponPolicyId) {
        return CouponPolicyDeleteRequest.builder()
                .couponPolicyId(couponPolicyId)
                .build();
    }

    public static CouponPolicyDeleteRequest from(CouponPolicy couponPolicy){
        return CouponPolicyDeleteRequest.builder()
                .couponPolicyId(couponPolicy.getId())
                .build();
    }
}
