package shop.bookbom.shop.domain.coupon.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddBookCouponRequest {
    @NotBlank(message = "쿠폰 이름을 입력하세요.")
    private String name;
    @NotNull(message = "쿠폰 정책을 입력하세요.")
    private Long couponPolicyId;
    @NotNull(message = "쿠폰 적용가능한 책을 입력하세요.")
    private Long bookId;

    @Builder
    private AddBookCouponRequest(String name, Long couponPolicyId, Long bookId) {
        this.name = name;
        this.couponPolicyId = couponPolicyId;
        this.bookId = bookId;
    }

    public AddBookCouponRequest of(String name, Long couponPolicyId, Long bookId){
        return AddBookCouponRequest.builder()
                .name(name)
                .couponPolicyId(couponPolicyId)
                .bookId(bookId)
                .build();
    }
}
