package shop.bookbom.shop.domain.order.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeforeOrderRequest {
    private Long bookId;

    private Integer quantity;
}
