package shop.bookbom.shop.domain.coupon.dto.request;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddBookCouponRequest {
    @NotBlank(message = "쿠폰 이름을 입력하세요.")
    private String name;
    @NotBlank(message = "쿠폰 정책을 입력하세요.")
    private Long couponPolicyId;
    private Date period;
    @NotBlank(message = "쿠폰 적용가능한 책을 입력하세요.")
    private Long bookId;

    @Builder
    private AddBookCouponRequest(String name, Long couponPolicyId, Date period, Long bookId) {
        this.name = name;
        this.couponPolicyId = couponPolicyId;
        this.period = period;
        this.bookId = bookId;
    }

    public AddBookCouponRequest of(String name, Long couponPolicyId, Date period, Long bookId){
        return AddBookCouponRequest.builder()
                .name(name)
                .couponPolicyId(couponPolicyId)
                .period(period)
                .bookId(bookId)
                .build();
    }
}
