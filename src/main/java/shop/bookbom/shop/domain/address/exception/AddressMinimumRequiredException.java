package shop.bookbom.shop.domain.address.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class AddressMinimumRequiredException extends BaseException {
    public AddressMinimumRequiredException() {
        super(ErrorCode.ADDRESS_MINIMUM_REQUIRED);
    }
}
