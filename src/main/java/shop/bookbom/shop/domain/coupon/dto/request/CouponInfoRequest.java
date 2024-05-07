package shop.bookbom.shop.domain.coupon.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CouponInfoRequest {
    private String type;

    @Builder
    private CouponInfoRequest(String type) {
        this.type = type;
    }

    public CouponInfoRequest of(String type) {
        return CouponInfoRequest.builder()
                .type(type)
                .build();
    }
}
