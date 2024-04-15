package shop.bookbom.shop.domain.wish.service;

import java.util.List;
import shop.bookbom.shop.domain.wish.dto.request.WishAddDeleteRequest;
import shop.bookbom.shop.domain.wish.dto.response.WishInfoResponse;
import shop.bookbom.shop.domain.wish.dto.response.WishTotalCountResponse;

public interface WishService {
    void addWish(List<WishAddDeleteRequest> items, Long userId);
    void deleteWish(List<WishAddDeleteRequest> items, Long userId);
    List<WishInfoResponse> getWishInfo(Long userId);
    WishTotalCountResponse getWishTotalCount(Long userId);
}
