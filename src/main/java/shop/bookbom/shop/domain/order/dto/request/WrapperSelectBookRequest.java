package shop.bookbom.shop.domain.order.dto.request;

import lombok.Data;

@Data
public class WrapperSelectBookRequest {
    private String bookTitle;
    private String imgUrl;
    private String wrapperName;
    private Integer quantity;
    private Integer cost;

    public WrapperSelectBookRequest(String bookTitle, String imgUrl, String wrapperName, Integer quantity,
                                    Integer cost) {
        this.bookTitle = bookTitle;
        this.imgUrl = imgUrl;
        this.wrapperName = wrapperName;
        this.quantity = quantity;
        this.cost = cost;
    }
}
