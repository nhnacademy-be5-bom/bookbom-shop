package shop.bookbom.shop.domain.order.dto.request;

import lombok.Data;

@Data
public class BeforeOrderRequest {
    private Long bookId;

    private Integer quantity;

    public BeforeOrderRequest(Long bookId, Integer quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }
}
