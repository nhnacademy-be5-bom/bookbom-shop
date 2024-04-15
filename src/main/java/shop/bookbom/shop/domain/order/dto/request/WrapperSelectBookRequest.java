package shop.bookbom.shop.domain.order.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WrapperSelectBookRequest {
    @NotBlank
    private String bookTitle;
    @NotBlank
    private String imgUrl;
    private String wrapperName;
    @Min(value = 1, message = "수량은 한 개 이상이어야 합니다.")
    private Integer quantity;
    @Min(value = 1, message = "가격은 최소 1원입니다")
    private Integer cost;

}
