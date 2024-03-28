package shop.bookbom.shop.coupon.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.order.entity.OrderCoupon;

@Entity
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_policy_id")
    private CouponPolicy couponPolicy;

    @OneToMany(mappedBy = "coupon")
    private List<MemberCoupon> memberCoupons;

    @OneToMany(mappedBy = "coupon")
    private List<CouponBook> couponBooks;

    @OneToMany(mappedBy = "coupon")
    private List<OrderCoupon> orderCoupons;

    @Builder
    public Coupon(String name, CouponPolicy couponPolicy, List<MemberCoupon> memberCoupons,
                  List<CouponBook> couponBooks) {
        this.name = name;
        this.couponPolicy = couponPolicy;
        this.memberCoupons = memberCoupons;
        this.couponBooks = couponBooks;
    }
}
