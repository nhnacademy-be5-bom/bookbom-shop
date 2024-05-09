package shop.bookbom.shop.domain.coupon.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.bookbom.shop.domain.coupon.entity.Coupon;
import shop.bookbom.shop.domain.coupon.entity.CouponType;

@Getter
@RequiredArgsConstructor
public class CouponIssueResponse {
    private Long couponId;
    private String name;
    private CouponType type;

    @Builder
    private CouponIssueResponse(Long couponId, String name, CouponType type) {
        this.couponId = couponId;
        this.name = name;
        this.type = type;
    }

    static public CouponIssueResponse from(Coupon coupon) {
        return CouponIssueResponse.builder()
                .couponId(coupon.getId())
                .name(coupon.getName())
                .type(coupon.getType())
                .build();
    }
}
