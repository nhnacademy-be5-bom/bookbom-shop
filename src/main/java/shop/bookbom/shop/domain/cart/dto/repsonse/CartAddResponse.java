package shop.bookbom.shop.domain.cart.dto.repsonse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartAddResponse {
    @JsonProperty(value = "cart_id")
    private Long cartId;
}
