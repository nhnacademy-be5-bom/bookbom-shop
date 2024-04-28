package shop.bookbom.shop.domain.order.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WrapperSelectBookRequest {
    @NotNull
    private Long bookId;
    private String wrapperName;
    @Min(value = 1, message = "수량은 한 개 이상이어야 합니다.")
    private Integer quantity;

}
