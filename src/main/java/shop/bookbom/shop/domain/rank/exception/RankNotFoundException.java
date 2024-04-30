package shop.bookbom.shop.domain.rank.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class RankNotFoundException extends BaseException {
    public RankNotFoundException() {
        super(ErrorCode.COMMON_ENTITY_NOT_FOUND, "존재하지 않는 등급 입니다.");
    }
}
