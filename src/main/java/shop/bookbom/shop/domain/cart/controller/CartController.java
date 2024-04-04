package shop.bookbom.shop.domain.cart.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.cart.dto.repsonse.CartInfoResponse;
import shop.bookbom.shop.domain.cart.dto.repsonse.CartUpdateResponse;
import shop.bookbom.shop.domain.cart.dto.request.CartAddRequest;
import shop.bookbom.shop.domain.cart.dto.request.CartUpdateRequest;
import shop.bookbom.shop.domain.cart.exception.CartInvalidAddRequestException;
import shop.bookbom.shop.domain.cart.service.CartService;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    /**
     * 장바구니 상품을 추가하는 메서드입니다.
     *
     * @param userId   로그인한 회원 ID
     * @param requests 장바구니 추가할 상품 ID와 수량 리스트
     * @return 장바구니 ID, 상품 ID와 수량 리스트
     */
    @PostMapping("/carts/{id}")
    public CommonResponse<CartInfoResponse> addToCart(
            @PathVariable("id") Long userId,
            @RequestBody List<CartAddRequest> requests
    ) {
        if (!isValidAddRequest(requests)) {
            throw new CartInvalidAddRequestException();
        }
        return CommonResponse.successWithData(cartService.addCart(requests, userId));
    }

    /**
     * 장바구니 상품을 조회하는 메서드입니다.
     *
     * @param userId 로그인한 회원 ID
     * @return 장바구니 ID, 상품 ID와 수량 리스트
     */
    @GetMapping("/carts/{id}")
    public CommonResponse<CartInfoResponse> getCart(@PathVariable("id") Long userId) {
        return CommonResponse.successWithData(cartService.getCartInfo(userId));
    }

    /**
     * 장바구니 상품의 수량을 변경하는 리스트입니다.
     *
     * @param id      장바구니 상품 ID
     * @param request 변경할 수량
     * @return 변경 완료된 수량
     */
    @PutMapping("/carts/items/{id}")
    public CommonResponse<CartUpdateResponse> updateQuantity(
            @PathVariable("id") Long id,
            @RequestBody @Valid CartUpdateRequest request
    ) {
        return CommonResponse.successWithData(cartService.updateQuantity(id, request.getQuantity()));
    }

    /**
     * 장바구니 상품을 삭제하는 메서드입니다.
     *
     * @param id 장바구니 상품 ID
     */
    @DeleteMapping("/carts/items/{id}")
    public CommonResponse<Void> deleteItem(@PathVariable("id") Long id) {
        cartService.deleteItem(id);
        return CommonResponse.success();
    }

    /**
     * 장바구니 추가할 상품 리스트를 검증하는 메서드입니다.
     *
     * @param requests 장바구니 추가할 상품 ID와 수량 리스트
     * @return 검증 결과
     */
    private boolean isValidAddRequest(List<CartAddRequest> requests) {
        return requests.stream()
                .allMatch(cartAddRequest -> cartAddRequest.getBookId() != null
                        && cartAddRequest.getQuantity() >= 1);
    }
}

