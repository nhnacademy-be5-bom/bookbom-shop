package shop.bookbom.shop.domain.cart.service;

import shop.bookbom.shop.domain.cart.entity.Cart;

public interface CartFindService {
    /**
     * 장바구니 객체를 반환하는 메서드입니다.
     *
     * @param userId 로그인 회원 ID
     * @return Cart
     */
    Cart getCart(Long userId);
}
