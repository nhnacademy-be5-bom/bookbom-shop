package shop.bookbom.shop.domain.wrapper.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class WrapperNotFoundException extends BaseException {
    public WrapperNotFoundException() {
        super(ErrorCode.WRAPPER_NOT_FOUND);
    }
}
