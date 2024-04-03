package shop.bookbom.shop.domain.cart.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CartAddRequest {
    @JsonProperty(value = "book_id")
    private Long bookId;
    private int quantity;
}
