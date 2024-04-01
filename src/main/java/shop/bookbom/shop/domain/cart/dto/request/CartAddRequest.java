package shop.bookbom.shop.domain.cart.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartAddRequest {
    @NotNull(message = "상품 아이디는 필수로 입력해주셔야 합니다.")
    @JsonProperty(value = "book_id")
    private Long bookId;
    @Min(value = 1, message = "최소 1개 이상 담아야 합니다.")
    private int quantity;
}
