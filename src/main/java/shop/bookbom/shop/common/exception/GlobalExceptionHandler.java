package shop.bookbom.shop.common.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.bookbom.shop.common.CommonResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public CommonResponse<Void> handleBaseException(BaseException e) {
        return CommonResponse.fail(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder sb = new StringBuilder();
        e.getBindingResult()
                .getAllErrors()
                .forEach(error -> sb.append(error.getDefaultMessage()).append(","));
        return CommonResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER, sb.toString());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public CommonResponse<Void> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return CommonResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER, e.getMessage());
    }
}
