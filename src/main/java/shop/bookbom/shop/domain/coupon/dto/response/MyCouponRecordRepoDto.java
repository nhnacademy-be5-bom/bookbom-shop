package shop.bookbom.shop.domain.coupon.dto.response;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.membercoupon.entity.CouponStatus;

@Getter
@NoArgsConstructor
public class MyCouponRecordRepoDto {
    private String name;
    private CouponStatus status;
    private LocalDate issueDate;
    private LocalDate useDate;
    private LocalDate expireDate;
}
