package shop.bookbom.shop.domain.orderbook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class OrderBookInfoDto {
    private String title;
    private String imgUrl;
    private Integer cost;
    private Integer quantity;

    public OrderBookInfoDto(String title, String imgUrl, Integer cost, Integer quantity) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.cost = cost;
        this.quantity = quantity;
    }
}
