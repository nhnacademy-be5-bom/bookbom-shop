package shop.bookbom.shop.domain.coupon.dto.response;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.membercoupon.entity.CouponStatus;

@Getter
@NoArgsConstructor
public class MyCouponRecordResponse {
    private String name;
    private CouponStatus status;
    private LocalDate date;

    @Builder
    private MyCouponRecordResponse(String name, CouponStatus status, LocalDate date) {
        this.name = name;
        this.status = status;
        this.date = date;
    }

    static public MyCouponRecordResponse of(String name, CouponStatus status, LocalDate date) {
        return MyCouponRecordResponse.builder()
                .name(name)
                .status(status)
                .date(date)
                .build();
    }
}
