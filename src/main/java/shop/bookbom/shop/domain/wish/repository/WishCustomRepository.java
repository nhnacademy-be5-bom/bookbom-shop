package shop.bookbom.shop.domain.wish.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.bookbom.shop.domain.wish.dto.response.WishInfoResponse;

public interface WishCustomRepository {
    Page<WishInfoResponse> getWishList(Long userId, Pageable pageable);
}
