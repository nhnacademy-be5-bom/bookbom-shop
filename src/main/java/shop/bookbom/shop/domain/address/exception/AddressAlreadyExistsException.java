package shop.bookbom.shop.domain.address.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class AddressAlreadyExistsException extends BaseException {
    public AddressAlreadyExistsException() {
        super(ErrorCode.ADDRESS_ALREADY_EXIST);
    }
}
