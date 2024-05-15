package shop.bookbom.shop.domain.membercoupon.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.coupon.dto.CouponDto;
import shop.bookbom.shop.domain.membercoupon.entity.CouponStatus;

@Getter
@NoArgsConstructor
public class MemberCouponDto {
    private Long id;
    private CouponStatus status;
    private LocalDate issueDate;
    private LocalDate expireDate;
    private LocalDate useDate;
    private Long couponId;
    private CouponDto couponDto;

    public MemberCouponDto(Long id, CouponStatus status, LocalDate issueDate, LocalDate expireDate, LocalDate useDate,
                           Long couponId) {
        this.id = id;
        this.status = status;
        this.issueDate = issueDate;
        this.expireDate = expireDate;
        this.useDate = useDate;
        this.couponId = couponId;
    }

    public void updateCouponDto(CouponDto couponDto) {
        this.couponDto = couponDto;
    }
}
