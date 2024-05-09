package shop.bookbom.shop.domain.membercoupon.dto.request;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IssueCouponRequest {
    private List<String> userEmailList;
    private Long couponId;
    private LocalDate expireDate;

    @Builder
    private IssueCouponRequest(List<String> userEmailList, Long couponId, LocalDate expireDate) {
        this.userEmailList = userEmailList;
        this.couponId = couponId;
        this.expireDate = expireDate;
    }

    public IssueCouponRequest of(List<String> userEmailList, Long couponId, LocalDate expireDate) {
        return IssueCouponRequest.builder()
                .userEmailList(userEmailList)
                .couponId(couponId)
                .expireDate(expireDate)
                .build();
    }
}
