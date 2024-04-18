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

    @Builder
    public WrapperSelectBookResponse(Long bookId, String bookTitle, String imgUrl, String wrapperName, Integer quantity,
                                     Integer cost) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.imgUrl = imgUrl;
        this.wrapperName = wrapperName;
        this.quantity = quantity;
        this.cost = cost;
    }
}
