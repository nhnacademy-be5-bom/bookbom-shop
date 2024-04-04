package shop.bookbom.shop.domain.order.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeforeOrderBookResponse {
    private String imageUrl;
    private String title;
    private Integer quantity;
    private Integer cost;
}
