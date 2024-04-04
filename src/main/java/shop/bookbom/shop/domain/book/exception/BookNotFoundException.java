package shop.bookbom.shop.domain.book.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class BookNotFoundException extends BaseException {
    public BookNotFoundException() {
        super(ErrorCode.COMMON_BOOK_NOT_FOUND);
    }
}
