package shop.bookbom.shop.domain.pointhistory.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class InvalidChangeReasonException extends BaseException {
    public InvalidChangeReasonException() {
        super(ErrorCode.POINT_INVALID_REASON);
    }
}
