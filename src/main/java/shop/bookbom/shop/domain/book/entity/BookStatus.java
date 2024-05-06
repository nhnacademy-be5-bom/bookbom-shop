package shop.bookbom.shop.domain.book.entity;

import lombok.Getter;

/**
 * /*Fs(For Sale) : 판매중
 * EOS(End Of Sale) : 판매종료
 * DEL(Delete) : 삭제
 */
@Getter
public enum BookStatus {
    FOR_SALE("판매중"),
    END_OF_SALE("판매 종료"),
    DEL("삭제"),
    SOLD_OUT("품절");

    private String value;

    BookStatus(String value) {
        this.value = value;
    }
}
