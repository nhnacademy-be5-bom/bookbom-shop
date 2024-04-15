package shop.bookbom.shop.domain.wish.dto.request;

import lombok.Getter;

@Getter
public class WishAddDeleteRequest {
    private Long bookId;

    public WishAddDeleteRequest(Long bookId) {
        this.bookId = bookId;
    }
}
