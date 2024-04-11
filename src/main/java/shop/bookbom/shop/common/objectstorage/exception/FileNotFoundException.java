package shop.bookbom.shop.common.objectstorage.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class FileNotFoundException extends BaseException {
    public FileNotFoundException() {
        super(ErrorCode.FILE_NOT_FOUND);
    }
}
