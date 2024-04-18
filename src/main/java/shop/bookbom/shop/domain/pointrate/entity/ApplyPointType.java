package shop.bookbom.shop.domain.pointrate.entity;

import lombok.Getter;

@Getter
public enum ApplyPointType {

    REVIEW("리뷰"),
    REGISTER("회원가입"),
    BOOK("도서"),
    RANK("등급"),
    ;
    private String value;

    ApplyPointType(String value) {
        this.value = value;
    }
}
