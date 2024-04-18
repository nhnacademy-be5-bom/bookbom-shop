package shop.bookbom.shop.common.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.bookbom.shop.common.CommonResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public CommonResponse<Void> handleBaseException(BaseException e) {
        return CommonResponse.fail(e.getErrorCode());
    }

}
