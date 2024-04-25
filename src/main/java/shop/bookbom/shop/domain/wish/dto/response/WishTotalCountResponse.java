package shop.bookbom.shop.domain.wish.dto.response;

import lombok.Getter;

@Getter
public class WishTotalCountResponse {
    private Long wishTotalCount;

    public WishTotalCountResponse(Long wishTotalCount) {
        this.wishTotalCount = wishTotalCount;
    }
}
