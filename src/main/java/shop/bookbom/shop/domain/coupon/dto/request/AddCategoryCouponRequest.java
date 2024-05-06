package shop.bookbom.shop.domain.coupon.dto.request;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AddCategoryCouponRequest {
    private String name;
    private Long couponPolicyId;
    private Date period;
    private Long categoryId;

    @Builder
    private AddCategoryCouponRequest(String name, Long couponPolicyId, Date period, Long categoryId) {
        this.name = name;
        this.couponPolicyId = couponPolicyId;
        this.period = period;
        this.categoryId = categoryId;
    }

    public AddCategoryCouponRequest of(String name, Long couponPolicyId, Date period, Long categoryId){
        return AddCategoryCouponRequest.builder()
                .name(name)
                .couponPolicyId(couponPolicyId)
                .period(period)
                .categoryId(categoryId)
                .build();
    }
}
