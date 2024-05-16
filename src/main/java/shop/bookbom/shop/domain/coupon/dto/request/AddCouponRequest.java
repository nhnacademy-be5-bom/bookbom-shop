package shop.bookbom.shop.domain.coupon.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddCouponRequest {
    @NotBlank(message = "쿠폰 이름을 입력하세요.")
    private String name;
    @NotNull(message = "쿠폰 정책을 입력하세요.")
    private Long couponPolicyId;

    @Builder
    private AddCouponRequest(String name, Long couponPolicyId) {
        this.name = name;
        this.couponPolicyId = couponPolicyId;
    }

    public AddCouponRequest of(String name, Long couponPolicyId) {
        return AddCouponRequest.builder()
                .name(name)
                .couponPolicyId(couponPolicyId)
                .build();
    }
}
