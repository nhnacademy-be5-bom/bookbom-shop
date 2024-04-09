package shop.bookbom.shop.domain.order.dto.response;

import lombok.Getter;

@Getter
public class BookTitleAndCostResponse {
    private String title;
    private Integer cost;


    public BookTitleAndCostResponse(String title, Integer cost) {
        this.title = title;
        this.cost = cost;

    }
}
