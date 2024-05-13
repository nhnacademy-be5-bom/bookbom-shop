package shop.bookbom.shop.security.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class TokenNotValidateException extends BaseException {
    public TokenNotValidateException(ErrorCode errorCode) {
        super(errorCode);
    }

    public TokenNotValidateException() {
        super(ErrorCode.JWT_NOT_VALIDATE, "토큰이 유효하지 않습니다.");
    }
}
