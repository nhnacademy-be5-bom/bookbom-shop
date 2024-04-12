package shop.bookbom.shop.domain.cart.dto.repsonse;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartInfoResponse {
    private Long cartId;
    private List<CartItemInfo> cartItems;

    @Builder
    public CartInfoResponse(Long cartId, List<CartItemInfo> cartItems) {
        this.cartId = cartId;
        this.cartItems = cartItems;
    }

    @Getter
    public static class CartItemInfo {
        private Long cartItemId;
        private Long bookId;
        private int quantity;

        @Builder
        public CartItemInfo(Long cartItemId, Long bookId, int quantity) {
            this.cartItemId = cartItemId;
            this.bookId = bookId;
            this.quantity = quantity;
        }
    }
}
