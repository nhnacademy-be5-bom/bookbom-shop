package shop.bookbom.shop.domain.order.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WrapperBookRequest {
    private String bookTitle;
    private String imgUrl;
    private String wrapperName;
    private Integer quantity;
    private Integer cost;
}

