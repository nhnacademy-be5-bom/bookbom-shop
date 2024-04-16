package shop.bookbom.shop.domain.booktag.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class BookTagAlreadyExistException extends BaseException {
    public BookTagAlreadyExistException() {
        super(ErrorCode.BOOK_TAG_ALREADY_EXIST);
    }

}
