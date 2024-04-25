package shop.bookbom.shop.domain.cart.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartAddRequest {
    private Long bookId;
    private int quantity;
}
