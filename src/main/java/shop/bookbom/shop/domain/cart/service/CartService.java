package shop.bookbom.shop.domain.cart.service;

import java.util.List;
import shop.bookbom.shop.domain.cart.dto.repsonse.CartInfoResponse;
import shop.bookbom.shop.domain.cart.dto.repsonse.CartUpdateResponse;
import shop.bookbom.shop.domain.cart.dto.request.CartAddRequest;

public interface CartService {

    /**
     * 장바구니에 상품을 추가하는 메서드입니다.
     *
     * @param addItems 추가할 상품 리스트
     * @param userId   회원 ID
     * @return 저장된 cartItem ID 리스트
     */
    List<Long> addCart(List<CartAddRequest> addItems, Long userId);

    /**
     * 장바구니에 담긴 상품 목록을 반환하는 메서드입니다.
     *
     * @param userId 회원 ID
     * @return 장바구니 ID, 장바구니 상품 정보 리스트 (상품 ID, 수량)
     */
    CartInfoResponse getCartInfo(Long userId);

    /**
     * 장바구니 상품 수량을 변경하는 메서드입니다.
     *
     * @param id       장바구니 상품 ID
     * @param quantity 변경할 수량
     * @return 변경 완료된 수량
     */
    CartUpdateResponse updateQuantity(Long id, int quantity);

    /**
     * 장바구니 상품을 삭제하는 메서드입니다.
     *
     * @param id 장바구니 상품 ID
     */
    void deleteItem(Long id);
}
