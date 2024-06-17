package shop.bookbom.shop.common;


import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import shop.bookbom.shop.common.exception.ErrorCode;

@Data
public class CommonResponse<T> {
    private static final String SUCCESS_MESSAGE = "SUCCESS";
    private ResponseHeader header;
    private T result;

    @Builder
    private CommonResponse(ResponseHeader header, T result) {
        this.header = header;
        this.result = result;
    }

    public static CommonResponse<Void> success() {
        return CommonResponse.<Void>builder()
                .header(ResponseHeader.builder()
                        .successful(true)
                        .resultCode(HttpStatus.OK.value())
                        .resultMessage(SUCCESS_MESSAGE).build())
                .build();
    }

    public static <T> CommonResponse<T> successWithData(T data) {
        return CommonResponse.<T>builder()
                .header(ResponseHeader.builder()
                        .successful(true)
                        .resultCode(HttpStatus.OK.value())
                        .resultMessage(SUCCESS_MESSAGE).build())
                .result(data)
                .build();
    }

    public static CommonResponse<Void> fail(ErrorCode errorCode) {
        return CommonResponse.<Void>builder()
                .header(ResponseHeader.builder()
                        .successful(false)
                        .resultCode(errorCode.getCode())
                        .resultMessage(errorCode.getMessage()).build())
                .build();
    }

    public static CommonResponse<Void> fail(ErrorCode errorCode, String message) {
        return CommonResponse.<Void>builder()
                .header(ResponseHeader.builder()
                        .successful(false)
                        .resultCode(errorCode.getCode())
                        .resultMessage(message).build())
                .build();
    }
}
