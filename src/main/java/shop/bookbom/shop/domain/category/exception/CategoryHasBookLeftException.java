package shop.bookbom.shop.domain.category.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class CategoryHasBookLeftException extends BaseException {
    public CategoryHasBookLeftException() {
        super(ErrorCode.CATEGORY_NAME_NOT_FOUND);
    }
}
