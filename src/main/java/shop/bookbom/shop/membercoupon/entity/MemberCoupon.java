package shop.bookbom.shop.membercoupon.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import shop.bookbom.shop.coupon.entity.Coupon;

@Entity
@Table(name = "member_coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_coupon_id")
    private Long id;

    @Column(name = "status")
    private CouponStatus status;

    @Column(name = "issue_date")
    private Date issueDate;

    @Column(name = "expire_date")
    private Date expireDate;

    @Column(name = "use_date")
    private Date useDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Builder
    public MemberCoupon(CouponStatus status, Date issueDate, Date expireDate, Date useDate,
                        Coupon coupon) {
        this.status = status;
        this.issueDate = issueDate;
        this.expireDate = expireDate;
        this.useDate = useDate;
        this.coupon = coupon;
    }
}
