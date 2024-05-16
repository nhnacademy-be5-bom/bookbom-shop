package shop.bookbom.shop.domain.users.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class WrongConfirmPasswordException extends BaseException {
    public WrongConfirmPasswordException() {
        super(ErrorCode.CONFIRM_PASSWORD_NOT_MATCH);
    }
}
