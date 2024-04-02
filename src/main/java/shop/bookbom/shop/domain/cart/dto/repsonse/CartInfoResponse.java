package shop.bookbom.shop.domain.cart.dto.repsonse;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartInfoResponse {
    @JsonProperty(value = "cart_id")
    private Long cartId;
    @JsonProperty(value = "cart_items")
    private List<CartItemInfo> cartItems;

    @Builder
    public CartInfoResponse(Long cartId, List<CartItemInfo> cartItems) {
        this.cartId = cartId;
        this.cartItems = cartItems;
    }

    @Getter
    public static class CartItemInfo {
        private Long bookId;
        private int quantity;

        @Builder
        public CartItemInfo(Long bookId, int quantity) {
            this.bookId = bookId;
            this.quantity = quantity;
        }
    }
}
