package shop.bookbom.shop.domain.order.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BeforeOrderBookResponse {
    private String imageUrl;
    private String title;
    private Integer quantity;
    private Integer cost;

    @Builder
    public BeforeOrderBookResponse(String imageUrl, String title, Integer quantity, Integer cost) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.quantity = quantity;
        this.cost = cost;
    }
}
