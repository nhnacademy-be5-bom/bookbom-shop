package shop.bookbom.shop.domain.coupon.dto.request;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddCouponRequest {
    @NotBlank(message = "쿠폰 이름을 입력하세요.")
    private String name;
    @NotBlank(message = "쿠폰 정책을 입력하세요.")
    private Long couponPolicyId;
    private Date period;

    @Builder
    private AddCouponRequest(String name, Long couponPolicyId, Date period) {
        this.name = name;
        this.couponPolicyId = couponPolicyId;
        this.period = period;
    }

    public AddCouponRequest of(String name, Long couponPolicyId, Date period) {
        return AddCouponRequest.builder()
                .name(name)
                .couponPolicyId(couponPolicyId)
                .period(period)
                .build();
    }
}
