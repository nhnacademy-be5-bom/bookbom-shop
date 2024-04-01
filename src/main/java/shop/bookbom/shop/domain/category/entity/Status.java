package shop.bookbom.shop.domain.category.entity;

import lombok.Getter;

@Getter
public enum Status {
    USED("사용중"),
    DEL("삭제");

    private String value;

    Status(String value) {
        this.value = value;
    }
}
