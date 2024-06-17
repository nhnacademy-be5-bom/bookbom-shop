package shop.bookbom.shop.domain.pointrate.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class PointRateNotFoundException extends BaseException {
    public PointRateNotFoundException() {
        super(ErrorCode.POINT_RATE_NOT_FOUND);

    }
}
