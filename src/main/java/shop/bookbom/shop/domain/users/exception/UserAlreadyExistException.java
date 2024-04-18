package shop.bookbom.shop.domain.users.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class UserAlreadyExistException extends BaseException {
    public UserAlreadyExistException() {
        super(ErrorCode.USER_ALREADY_EXIST, "이미 존재하는 사용자 입니다.");
    }
}
