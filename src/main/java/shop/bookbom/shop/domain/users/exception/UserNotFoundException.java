package shop.bookbom.shop.domain.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends BaseException {

    public UserNotFoundException() {
        super(ErrorCode.COMMON_ENTITY_NOT_FOUND, "존재하지 않는 사용자 입니다.");
    }
}
