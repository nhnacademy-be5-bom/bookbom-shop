package shop.bookbom.shop.domain.cart.dto.repsonse;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CartUpdateResponse {
    private int quantity;

    @Builder
    public CartUpdateResponse(int quantity) {
        this.quantity = quantity;
    }
}
