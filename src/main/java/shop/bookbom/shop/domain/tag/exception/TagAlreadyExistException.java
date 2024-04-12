package shop.bookbom.shop.domain.tag.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class TagAlreadyExistException extends BaseException {
    public TagAlreadyExistException() {
        super(ErrorCode.TAG_ALREADY_EXIST);
    }
}
