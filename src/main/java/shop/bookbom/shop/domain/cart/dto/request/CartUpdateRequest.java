package shop.bookbom.shop.domain.cart.dto.request;

import javax.validation.constraints.Min;
import lombok.Data;

@Data
public class CartUpdateRequest {
    @Min(value = 1, message = "최소 1개 이상 담아야 합니다.")
    private int quantity;
}
