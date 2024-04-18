package shop.bookbom.shop.domain.booktag.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class BookTagNotFoundException extends BaseException {
    public BookTagNotFoundException() {
        super(ErrorCode.BOOK_TAG_NOT_FOUND);
    }
}
