package shop.bookbom.shop.domain.author.exception;

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
public class AuthorIdNotFoundException extends BaseException {
    public AuthorIdNotFoundException() {
        super(ErrorCode.COMMON_INVALID_PARAMETER);
    }

    public AuthorIdNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AuthorIdNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
