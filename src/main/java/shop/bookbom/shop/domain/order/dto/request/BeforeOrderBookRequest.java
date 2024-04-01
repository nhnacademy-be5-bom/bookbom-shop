package shop.bookbom.shop.domain.order.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeforeOrderBookRequest {
    private Long bookId;

    private Integer quantity;
}
