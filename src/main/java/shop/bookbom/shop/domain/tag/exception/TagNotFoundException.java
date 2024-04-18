package shop.bookbom.shop.domain.tag.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class TagNotFoundException extends BaseException {
    public TagNotFoundException() {
        super(ErrorCode.TAG_NOT_FOUND);
    }
}
