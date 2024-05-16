package shop.bookbom.shop.domain.review.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class ReviewOrderBookNotFoundException extends BaseException {
    public ReviewOrderBookNotFoundException() {
        super(ErrorCode.REVIEW_ORDER_BOOK_NOT_FOUND);
    }
}
