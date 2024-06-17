package shop.bookbom.shop.domain.wish.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WishInfoResponse {
    private Long id;
    private String title;
    private String publisher;
    private Integer cost;
    private Integer discountCost;
    private String url;

    @Builder
    public WishInfoResponse(Long id, String title, String publisher, Integer cost, Integer discountCost, String url) {
        this.id = id;
        this.title = title;
        this.publisher = publisher;
        this.cost = cost;
        this.discountCost = discountCost;
        this.url = url;
    }
}
