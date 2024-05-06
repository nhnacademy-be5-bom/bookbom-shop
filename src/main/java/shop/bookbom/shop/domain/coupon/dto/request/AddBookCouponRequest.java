package shop.bookbom.shop.domain.coupon.dto.request;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AddBookCouponRequest {
    private String name;
    private Long couponPolicyId;
    private Date period;
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
