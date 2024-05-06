package shop.bookbom.shop.domain.coupon.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import shop.bookbom.shop.domain.couponbook.entity.CouponBook;
import shop.bookbom.shop.domain.couponpolicy.entity.CouponPolicy;
import shop.bookbom.shop.domain.membercoupon.entity.MemberCoupon;
import shop.bookbom.shop.domain.ordercoupon.entity.OrderCoupon;

@Entity
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_policy_id", nullable = false)
    private CouponPolicy couponPolicy;

    @OneToMany(mappedBy = "coupon")
    private List<MemberCoupon> memberCoupons = new ArrayList<>();

    @OneToMany(mappedBy = "coupon")
    private List<CouponBook> couponBooks = new ArrayList<>();

    @Builder
    public Coupon(
            String name,
            CouponType type,
            CouponPolicy couponPolicy,
            List<MemberCoupon> memberCoupons,
            List<CouponBook> couponBooks
    ) {
        this.name = name;
        this.type = type;
        this.couponPolicy = couponPolicy;
        this.memberCoupons = memberCoupons;
        this.couponBooks = couponBooks;
    }
}
