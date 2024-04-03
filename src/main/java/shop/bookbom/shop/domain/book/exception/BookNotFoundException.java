package shop.bookbom.shop.domain.book.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class BookNotFoundException extends BaseException {
    public BookNotFoundException() {
        super(ErrorCode.BOOK_NOT_FOUND);
    }

    public BookNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BookNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
