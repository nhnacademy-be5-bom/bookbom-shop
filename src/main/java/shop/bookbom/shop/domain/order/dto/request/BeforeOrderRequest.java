package shop.bookbom.shop.domain.order.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BeforeOrderRequest {
    @NotNull
    private Long bookId;
    @Min(value = 1, message = "수량은 한 개 이상이어야 합니다.")
    private Integer quantity;

    public BeforeOrderRequest(Long bookId, Integer quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }
}
