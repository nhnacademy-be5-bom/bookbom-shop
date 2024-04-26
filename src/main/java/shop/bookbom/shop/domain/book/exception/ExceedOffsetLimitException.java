package shop.bookbom.shop.domain.book.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

/**
 * packageName    : shop.bookbom.shop.domain.book.exception
 * fileName       : ExceedOffsetLimitException
 * author         : UuLaptop
 * date           : 2024-04-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-26        UuLaptop       최초 생성
 */
public class ExceedOffsetLimitException extends BaseException {
    public ExceedOffsetLimitException() {
        super(ErrorCode.EXCEEDS_OFFSET_RANGE);
    }

    public ExceedOffsetLimitException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ExceedOffsetLimitException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
