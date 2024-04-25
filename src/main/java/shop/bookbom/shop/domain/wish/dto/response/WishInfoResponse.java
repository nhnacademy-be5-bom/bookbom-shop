package shop.bookbom.shop.domain.wish.dto.response;

import lombok.Getter;

@Getter
public class WishInfoResponse {
    private String title;
    private String publisher;
    private Integer cost;
    private Integer discountCost;

    public WishInfoResponse(String title, String publisher, Integer cost, Integer discountCost) {
        this.title = title;
        this.publisher = publisher;
        this.cost = cost;
        this.discountCost = discountCost;
    }
}
