package shop.bookbom.shop.domain.couponpolicy.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.coupon.entity.Coupon;

@Entity
@Table(name = "coupon_policy")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_policy_id")
    private Long id;

    @Column(name = "min_order_cost")
    private int minOrderCost;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type")
    private DiscountType discountType;

    @Column(name = "discount_cost")
    private Integer discountCost;

    @Column(name = "max_discount_cost")
    private Integer maxDiscountCost;

    @OneToMany(mappedBy = "couponPolicy")
    private List<Coupon> coupons;

    @Builder
    public CouponPolicy(int minOrderCost, DiscountType discountType, Integer discountCost,
                        Integer maxDiscountCost,
                        List<Coupon> coupons) {
        this.minOrderCost = minOrderCost;
        this.discountType = discountType;
        this.discountCost = discountCost;
        this.maxDiscountCost = maxDiscountCost;
        this.coupons = coupons;
    }
}
