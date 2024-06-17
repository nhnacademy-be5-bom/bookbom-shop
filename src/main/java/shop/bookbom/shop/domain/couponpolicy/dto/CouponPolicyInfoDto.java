package shop.bookbom.shop.domain.couponpolicy.dto;

import lombok.Builder;
import lombok.Getter;
import shop.bookbom.shop.domain.couponpolicy.entity.CouponPolicy;
import shop.bookbom.shop.domain.couponpolicy.entity.DiscountType;

@Getter
public class CouponPolicyInfoDto {
    private Long couponPolicyId;
    private DiscountType discountType;
    private int discountCost;
    private int minOrderCost;
    private int maxDiscountCost;

    @Builder
    private CouponPolicyInfoDto(Long couponPolicyId, DiscountType discountType, int discountCost,
                                int minOrderCost, int maxDiscountCost) {
        this.couponPolicyId = couponPolicyId;
        this.discountType = discountType;
        this.discountCost = discountCost;
        this.minOrderCost = minOrderCost;
        this.maxDiscountCost = maxDiscountCost;
    }

    public static CouponPolicyInfoDto of(Long couponPolicyId, DiscountType discountType, int discountCost,
                                  int minOrderCost, int maxDiscountCost){
        return CouponPolicyInfoDto.builder()
                .couponPolicyId(couponPolicyId)
                .discountType(discountType)
                .discountCost(discountCost)
                .minOrderCost(minOrderCost)
                .maxDiscountCost(maxDiscountCost)
                .build();
    }

    public static CouponPolicyInfoDto from(CouponPolicy couponPolicy) {
        return CouponPolicyInfoDto.builder()
                .couponPolicyId(couponPolicy.getId())
                .discountType(couponPolicy.getDiscountType())
                .discountCost(couponPolicy.getDiscountCost())
                .minOrderCost(couponPolicy.getMinOrderCost())
                .maxDiscountCost(couponPolicy.getMaxDiscountCost())
                .build();
    }
}
