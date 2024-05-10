package shop.bookbom.shop.domain.membercoupon.dto.request;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IssueCouponRequest {
    @NotEmpty(message = "적어도 한 명의 유저가 있어야 합니다.")
    private List<String> userEmailList;
    @NotBlank(message = "발급할 쿠폰을 입력해주세요.")
    private Long couponId;
    @NotBlank(message = "쿠폰의 만료일을 입력해주세요.")
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
