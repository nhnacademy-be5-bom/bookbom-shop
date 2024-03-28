package shop.bookbom.shop.order.entity;

import lombok.Getter;

@Getter
public enum OrderBookStatus {
    NONE("정상"),
    PART_RETURN("부분 반품"),
    RETURN("반품"),
    ;

    private String value;

    OrderBookStatus(String value) {
        this.value = value;
    }
}
