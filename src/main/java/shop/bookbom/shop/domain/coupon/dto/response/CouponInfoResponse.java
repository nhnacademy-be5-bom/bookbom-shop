package shop.bookbom.shop.domain.coupon.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.bookbom.shop.domain.coupon.entity.Coupon;
import shop.bookbom.shop.domain.coupon.entity.CouponType;
import shop.bookbom.shop.domain.couponbook.entity.CouponBook;
import shop.bookbom.shop.domain.couponpolicy.entity.DiscountType;

@Getter
@RequiredArgsConstructor
public class CouponInfoResponse {
    private Long id;
    private String name;
    private CouponType type;

    private DiscountType discountType;
    private int discountCost;
    private int minOrderCost;
    private int maxDiscountCost;

    private String title;
    private String categoryName;

    @Builder
    private CouponInfoResponse(Long couponId, String name, CouponType couponType, DiscountType discountType,
                               int discountCost, int minOrderCost, int maxDiscountCost, String bookName,
                               String categoryName) {
        this.id = couponId;
        this.name = name;
        this.type = couponType;
        this.discountType = discountType;
        this.discountCost = discountCost;
        this.minOrderCost = minOrderCost;
        this.maxDiscountCost = maxDiscountCost;
        this.title = bookName;
        this.categoryName = categoryName;
    }

    public static CouponInfoResponse from(Coupon coupon) {
        return CouponInfoResponse.builder()
                .couponId(coupon.getId())
                .name(coupon.getName())
                .couponType(coupon.getType())
                .discountType(coupon.getCouponPolicy().getDiscountType())
                .discountCost(coupon.getCouponPolicy().getDiscountCost())
                .minOrderCost(coupon.getCouponPolicy().getMinOrderCost())
                .maxDiscountCost(coupon.getCouponPolicy().getMaxDiscountCost())
                .build();
    }

    public void setBookName(String title) {
        this.title = title;
    }

    public void setCategoryName(String name) {
        categoryName = name;
    }
}
