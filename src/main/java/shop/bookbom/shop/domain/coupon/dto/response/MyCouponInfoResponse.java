package shop.bookbom.shop.domain.coupon.dto.response;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.couponpolicy.entity.DiscountType;
import shop.bookbom.shop.domain.membercoupon.entity.CouponStatus;

@Getter
@NoArgsConstructor
public class MyCouponInfoResponse {
    private String name;
    private CouponStatus status;
    private LocalDate expireDate;
    private DiscountType discountType;
    private int discountCost;
    private int minOrderCost;
    private int maxDiscountCost;
    private String title;
    private String categoryName;

    @Builder
    private MyCouponInfoResponse(String name, CouponStatus status, LocalDate expireDate, DiscountType discountType, int discountCost,
                                int minOrderCost, int maxDiscountCost, String title, String categoryName) {
        this.name = name;
        this.status = status;
        this.expireDate = expireDate;
        this.discountType = discountType;
        this.discountCost = discountCost;
        this.minOrderCost = minOrderCost;
        this.maxDiscountCost = maxDiscountCost;
        this.title = title;
        this.categoryName = categoryName;
    }

    static public MyCouponInfoResponse of(String name, CouponStatus status, LocalDate expireDate, DiscountType discountType, int discountCost,
                                          int minOrderCost, int maxDiscountCost, String title, String categoryName){
        return MyCouponInfoResponse.builder()
                .name(name)
                .status(status)
                .expireDate(expireDate)
                .discountType(discountType)
                .discountCost(discountCost)
                .minOrderCost(minOrderCost)
                .maxDiscountCost(maxDiscountCost)
                .title(title)
                .categoryName(categoryName)
                .build();
    }
}
