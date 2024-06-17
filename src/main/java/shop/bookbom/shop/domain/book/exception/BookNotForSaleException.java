package shop.bookbom.shop.domain.book.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class BookNotForSaleException extends BaseException {
    public BookNotForSaleException() {
        super(ErrorCode.BOOK_NOT_FOR_SALE);
    }
}
