package shop.bookbom.shop.domain.order.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookTitleAndCostResponse {
    private String title;
    private Integer cost;


    @Builder
    public BookTitleAndCostResponse(String title, Integer cost) {
        this.title = title;
        this.cost = cost;

    }
}
