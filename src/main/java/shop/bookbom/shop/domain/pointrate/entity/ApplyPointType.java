package shop.bookbom.shop.domain.pointrate.entity;

import lombok.Getter;

@Getter
public enum ApplyPointType {

    REVIEW_TEXT("일반 리뷰"),
    REVIEW_IMAGE("사진 리뷰"),
    REGISTER("회원가입"),
    BOOK("도서"),
    RANK("등급"),
    ;
    private final String value;

    ApplyPointType(String value) {
        this.value = value;
    }
}
