package shop.bookbom.shop.domain.order.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WrapperSelectBookResponse {
    private Long bookId;
    private String bookTitle;
    private String imgUrl;
    private String wrapperName;
    private Integer quantity;
    private Integer cost;
    private Integer discountCost;

    @Builder
    public WrapperSelectBookResponse(Long bookId, String bookTitle, String imgUrl, String wrapperName, Integer quantity,
                                     Integer cost, Integer discountCost) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.imgUrl = imgUrl;
        this.wrapperName = wrapperName;
        this.quantity = quantity;
        this.cost = cost;
        this.discountCost = discountCost;
    }
}
