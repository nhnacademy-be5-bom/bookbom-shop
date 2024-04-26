package shop.bookbom.shop.domain.category.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class CategoryHasChildLeftException extends BaseException {
    public CategoryHasChildLeftException() {
        super(ErrorCode.CATEGORY_NAME_NOT_FOUND);
    }
}
