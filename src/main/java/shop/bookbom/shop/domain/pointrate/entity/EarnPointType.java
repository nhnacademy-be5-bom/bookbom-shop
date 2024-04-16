package shop.bookbom.shop.domain.pointrate.entity;

import lombok.Getter;

@Getter
public enum EarnPointType {
    COST("금액"),
    RATE("비율");

    private String value;

    EarnPointType(String value) {
        this.value = value;
    }
}
