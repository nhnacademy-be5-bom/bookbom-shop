package shop.bookbom.shop.domain.pointhistory.entity;

import lombok.Getter;

@Getter
public enum PointHistoryDetail {
    ORDER_EARN("주문 적립"),
    PAYMENT_USE("적립금 결제"),
    REVIEW("리뷰 적립"),
    SIGN_UP("회원가입"),
    ;

    private final String value;

    PointHistoryDetail(String value) {
        this.value = value;
    }
}
