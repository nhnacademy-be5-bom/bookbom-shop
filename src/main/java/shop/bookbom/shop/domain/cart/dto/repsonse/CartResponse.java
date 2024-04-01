package shop.bookbom.shop.domain.cart.dto.repsonse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartResponse {
    private Long bookId;
    private int quantity;
}
