package shop.bookbom.shop.domain.book.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

/**
 * packageName    : shop.bookbom.shop.domain.book.exception
 * fileName       : BookIdMismatchException
 * author         : UuLaptop
 * date           : 2024-04-17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-17        UuLaptop       최초 생성
 */
public class IdMismatchException extends BaseException {
    public IdMismatchException() {
        super(ErrorCode.ID_AND_PATH_VARIABLE_DOES_NOT_MATCH);
    }

    public IdMismatchException(ErrorCode errorCode) {
        super(errorCode);
    }

    public IdMismatchException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
