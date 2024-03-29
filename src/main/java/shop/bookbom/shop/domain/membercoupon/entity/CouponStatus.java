package shop.bookbom.shop.domain.membercoupon.entity;

import lombok.Getter;

@Getter
public enum CouponStatus {
    NEW("사용 가능"),
    USED("사용 완료"),
    EXPIRED("만료"),
    ;

    private String value;

    CouponStatus(String value) {
        this.value = value;
    }
}
