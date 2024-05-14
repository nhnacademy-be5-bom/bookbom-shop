package shop.bookbom.shop.domain.address.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class AddressNotFoundException extends BaseException {
    public AddressNotFoundException() {
        super(ErrorCode.ADDRESS_NOT_FOUND);
    }
}
