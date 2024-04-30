package shop.bookbom.shop.domain.cart.repository;

import java.util.Optional;
import shop.bookbom.shop.domain.cart.entity.Cart;
import shop.bookbom.shop.domain.member.entity.Member;

public interface CartRepositoryCustom {
    /**
     * 장바구니와 장바구니 상품을 한번에 가져오는 메서드입니다.
     *
     * @param member 회원
     * @return 장바구니 엔티티 Optional
     */
    Optional<Cart> getCartFetchItems(Member member);
}
