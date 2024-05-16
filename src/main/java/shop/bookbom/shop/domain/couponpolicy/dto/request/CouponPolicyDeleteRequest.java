package shop.bookbom.shop.domain.couponpolicy.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.bookbom.shop.domain.couponpolicy.entity.CouponPolicy;

@Getter
@RequiredArgsConstructor
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

    public static CouponPolicyDeleteRequest from(CouponPolicy couponPolicy) {
        return CouponPolicyDeleteRequest.builder()
                .couponPolicyId(couponPolicy.getId())
                .build();
    }
}
