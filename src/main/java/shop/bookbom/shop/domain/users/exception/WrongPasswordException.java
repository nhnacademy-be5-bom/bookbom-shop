package shop.bookbom.shop.domain.users.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class WrongPasswordException extends BaseException {
    public WrongPasswordException() {
        super(ErrorCode.PASSWORD_NOT_MATCH);
    }
}
