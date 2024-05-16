package shop.bookbom.shop.domain.couponpolicy.dto.request;

import lombok.Builder;
import lombok.Getter;
import shop.bookbom.shop.domain.couponpolicy.dto.CouponPolicyInfoDto;
import shop.bookbom.shop.domain.couponpolicy.entity.CouponPolicy;
import shop.bookbom.shop.domain.couponpolicy.entity.DiscountType;

@Getter
public class CouponPolicyAddRequest {
    private DiscountType discountType;
    private int discountCost;
    private int minOrderCost;
    private int maxDiscountCost;

    @Builder
    private CouponPolicyAddRequest(DiscountType discountType, int discountCost, int minOrderCost,
                                  int maxDiscountCost) {
        this.discountType = discountType;
        this.discountCost = discountCost;
        this.minOrderCost = minOrderCost;
        this.maxDiscountCost = maxDiscountCost;
    }

    public CouponPolicyAddRequest of(DiscountType discountType, int discountCost,
                                  int minOrderCost, int maxDiscountCost){
        return CouponPolicyAddRequest.builder()
                .discountType(discountType)
                .discountCost(discountCost)
                .minOrderCost(minOrderCost)
                .maxDiscountCost(maxDiscountCost)
                .build();
    }

    public CouponPolicyAddRequest from(CouponPolicy couponPolicy) {
        return CouponPolicyAddRequest.builder()
                .discountType(couponPolicy.getDiscountType())
                .discountCost(couponPolicy.getDiscountCost())
                .minOrderCost(couponPolicy.getMinOrderCost())
                .maxDiscountCost(couponPolicy.getMaxDiscountCost())
                .build();
    }
}
