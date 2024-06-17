package shop.bookbom.shop.domain.address.exception;

import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;

public class AddressLimitExceedException extends BaseException {
    public AddressLimitExceedException() {
        super(ErrorCode.ADDRESS_LIMIT_EXCEED);
    }
}
