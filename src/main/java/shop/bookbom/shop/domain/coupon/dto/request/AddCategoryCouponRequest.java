package shop.bookbom.shop.domain.coupon.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddCategoryCouponRequest {
    @NotBlank(message = "쿠폰 이름을 입력하세요.")
    private String name;
    @NotNull(message = "쿠폰 정책을 입력하세요.")
    private Long couponPolicyId;
    @NotNull(message = "쿠폰 적용가능한 카테고리를 입력하세요.")
    private Long categoryId;

    @Builder
    private AddCategoryCouponRequest(String name, Long couponPolicyId, Long categoryId) {
        this.name = name;
        this.couponPolicyId = couponPolicyId;
        this.categoryId = categoryId;
    }

    public AddCategoryCouponRequest of(String name, Long couponPolicyId, Long categoryId){
        return AddCategoryCouponRequest.builder()
                .name(name)
                .couponPolicyId(couponPolicyId)
                .categoryId(categoryId)
                .build();
    }
}
