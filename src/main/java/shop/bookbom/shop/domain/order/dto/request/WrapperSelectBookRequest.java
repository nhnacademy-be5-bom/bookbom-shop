package shop.bookbom.shop.domain.order.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WrapperSelectBookRequest {
    @NotNull
    private String bookTitle;
    @NotNull
    private String imgUrl;
    private String wrapperName;
    @Min(value = 1, message = "수량은 한 개 이상이어야 합니다.")
    private Integer quantity;
    @Min(value = 1, message = "가격은 최소 1원입니다")
    private Integer cost;

    public WrapperSelectBookRequest(String bookTitle, String imgUrl, String wrapperName, Integer quantity,
                                    Integer cost) {
        this.bookTitle = bookTitle;
        this.imgUrl = imgUrl;
        this.wrapperName = wrapperName;
        this.quantity = quantity;
        this.cost = cost;
    }
}
