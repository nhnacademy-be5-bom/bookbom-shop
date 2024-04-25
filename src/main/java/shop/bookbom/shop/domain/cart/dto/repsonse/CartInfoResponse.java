package shop.bookbom.shop.domain.cart.dto.repsonse;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartInfoResponse {
    private Long cartId;
    private List<CartItemDto> cartItems;

    @Builder
    public CartInfoResponse(Long cartId, List<CartItemDto> cartItems) {
        this.cartId = cartId;
        this.cartItems = cartItems;
    }

    public static CartInfoResponse of(Long cartId, List<CartItemDto> cartItems) {
        return CartInfoResponse.builder()
                .cartId(cartId)
                .cartItems(cartItems)
                .build();
    }
}
