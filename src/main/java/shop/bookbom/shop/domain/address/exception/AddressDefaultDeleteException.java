package shop.bookbom.shop.domain.address.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class AddressDefaultDeleteException extends BaseException {
    public AddressDefaultDeleteException() {
        super(ErrorCode.ADDRESS_DEFAULT_DELETE);
    }
}
