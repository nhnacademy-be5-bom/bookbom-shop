package shop.bookbom.shop.common.exception;

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
public class InvalidParameterException extends BaseException {
    public InvalidParameterException() {
        super(ErrorCode.COMMON_INVALID_PARAMETER);
    }

    public InvalidParameterException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidParameterException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public InvalidParameterException(String message) {
        super(ErrorCode.COMMON_INVALID_PARAMETER, message);
    }
}
