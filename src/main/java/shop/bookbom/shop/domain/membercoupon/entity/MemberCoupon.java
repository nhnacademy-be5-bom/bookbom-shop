package shop.bookbom.shop.domain.membercoupon.entity;

import java.time.LocalDate;
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
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.coupon.entity.Coupon;

@Entity
@Table(name = "member_coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_coupon_id", nullable = false)
    private Long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CouponStatus status;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "expire_date", nullable = false)
    private LocalDate expireDate;

    @Column(name = "use_date")
    private LocalDate useDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @Builder
    public MemberCoupon(
            CouponStatus status,
            LocalDate issueDate,
            LocalDate expireDate,
            LocalDate useDate,
            Coupon coupon
    ) {
        this.status = status;
        this.issueDate = issueDate;
        this.expireDate = expireDate;
        this.useDate = useDate;
        this.coupon = coupon;
    }

    public void updateUseDate(LocalDate useDate) {
        this.useDate = useDate;
    }

    public void updateCouponStatus(CouponStatus status) {
        this.status = status;
    }
}
