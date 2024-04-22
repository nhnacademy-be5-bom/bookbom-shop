package shop.bookbom.shop.domain.users.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class RoleNotFoundException extends BaseException {
    public RoleNotFoundException() {
        super(ErrorCode.COMMON_ENTITY_NOT_FOUND, "존재하지 않는 역할 입니다.");
    }
}
