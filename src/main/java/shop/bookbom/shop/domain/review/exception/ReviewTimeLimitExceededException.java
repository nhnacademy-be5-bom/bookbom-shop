package shop.bookbom.shop.domain.review.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class ReviewTimeLimitExceededException extends BaseException {
    public ReviewTimeLimitExceededException() {
        super(ErrorCode.REVIEW_TIME_LIMIT_EXCEED);
    }
}
