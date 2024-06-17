package shop.bookbom.shop.domain.wish.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.bookbom.shop.domain.wish.dto.response.WishInfoResponse;

public interface WishService {
    void addWish(List<Long> items, Long userId);
    void deleteWish(Long wishId, Long userId);
    Page<WishInfoResponse> getWishInfo(Long userId, Pageable pageable);
}
