package shop.bookbom.shop.domain.cart.dto.request;

import lombok.Data;

@Data
public class CartAddRequest {
    private Long bookId;
    private int quantity;
}
