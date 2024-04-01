package shop.bookbom.shop.domain.book.entity;

import lombok.Getter;

/**
 * /*Fs(For Sale) : 판매중
 * EOS(End Of Sale) : 판매종료
 * DEL(Delete) : 삭제
 */
@Getter
public enum BookStatus {
    FS("판매중"),
    EOS("판매 종료"),
    DEL("삭제"),
    ;

    private String value;

    BookStatus(String value) {
        this.value = value;
    }
}
