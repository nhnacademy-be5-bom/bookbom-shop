package shop.bookbom.shop.domain.rank.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class RankNotFoundException extends BaseException {
    public RankNotFoundException() {
        super(ErrorCode.RANK_NOT_FOUND);
    }
}
