package shop.bookbom.shop.domain.category.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class NoSuchCategoryNameException extends BaseException {
    public NoSuchCategoryNameException() {
        super(ErrorCode.CATEGORY_NAME_NOT_FOUND);
    }
}
