package shop.bookbom.shop.domain.coupon.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.coupon.entity.CouponType;
import shop.bookbom.shop.domain.couponbook.dto.CouponBookDto;
import shop.bookbom.shop.domain.couponcategory.dto.CouponCategoryDto;
import shop.bookbom.shop.domain.couponpolicy.entity.DiscountType;

@Getter
@NoArgsConstructor
public class CouponDto {
    private Long id;
    private String name;
    private CouponType type;
    //couponPolicy
    private int minOrderCost;
    private DiscountType discountType;
    private Integer discountCost;
    private Integer maxDiscountCost;

    private List<CouponBookDto> couponBooks;
    private List<CouponCategoryDto> couponCategories;

    public CouponDto(Long id, String name, CouponType type, int minOrderCost, DiscountType discountType,
                     Integer discountCost, Integer maxDiscountCost) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.minOrderCost = minOrderCost;
        this.discountType = discountType;
        this.discountCost = discountCost;
        this.maxDiscountCost = maxDiscountCost;
    }

    public void updateCouponBooks(List<CouponBookDto> couponBooks) {
        this.couponBooks = couponBooks;
    }

    public void updateCouponCategories(
            List<CouponCategoryDto> couponCategories) {
        this.couponCategories = couponCategories;
    }
}
