package shop.bookbom.shop.domain.file.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

/**
 * packageName    : shop.bookbom.shop.domain.file.exception
 * fileName       : ThumbNailNotFoundException
 * author         : UuLaptop
 * date           : 2024-04-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-22        UuLaptop       최초 생성
 */
public class ThumbNailNotFoundException extends BaseException {
    public ThumbNailNotFoundException() {
        super(ErrorCode.FILE_NOT_FOUND);
    }
}
