package shop.bookbom.shop.domain.cart.controller;

import static shop.bookbom.shop.common.CommonListResponse.successWithList;
import static shop.bookbom.shop.common.CommonResponse.success;
import static shop.bookbom.shop.common.CommonResponse.successWithData;

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
import shop.bookbom.shop.annotation.Login;
import shop.bookbom.shop.common.CommonListResponse;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.cart.dto.repsonse.CartInfoResponse;
import shop.bookbom.shop.domain.cart.dto.repsonse.CartUpdateResponse;
import shop.bookbom.shop.domain.cart.dto.request.CartAddRequest;
import shop.bookbom.shop.domain.cart.dto.request.CartUpdateRequest;
import shop.bookbom.shop.domain.cart.exception.CartInvalidAddRequestException;
import shop.bookbom.shop.domain.cart.service.CartService;
import shop.bookbom.shop.domain.users.dto.UserDto;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    /**
     * 장바구니 상품을 추가하는 메서드입니다.
     *
     * @param userDto  로그인한 회원 정보
     * @param requests 장바구니 추가할 상품 ID와 수량 리스트
     */
    @PostMapping("/carts")
    public CommonListResponse<Long> addToCart(
            @Login UserDto userDto,
            @RequestBody List<CartAddRequest> requests
    ) {
        if (!isValidAddRequest(requests)) {
            throw new CartInvalidAddRequestException();
        }
        return successWithList(cartService.addCart(requests, userDto.getId()));
    }

    /**
     * 장바구니 상품을 조회하는 메서드입니다.
     *
     * @param userDto 로그인한 회원 정보
     * @return 장바구니 ID, 상품 ID와 수량 리스트
     */
    @GetMapping("/carts")
    public CommonResponse<CartInfoResponse> getCart(@Login UserDto userDto) {
        return successWithData(cartService.getCartInfo(userDto.getId()));
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
            @Login UserDto userDto,
            @PathVariable("id") Long id,
            @RequestBody @Valid CartUpdateRequest request
    ) {
        return successWithData(cartService.updateQuantity(userDto.getId(), id, request.getQuantity()));
    }

    /**
     * 장바구니 상품을 삭제하는 메서드입니다.
     *
     * @param id 장바구니 상품 ID
     */
    @DeleteMapping("/carts/items/{id}")
    public CommonResponse<Void> deleteItem(@Login UserDto userDto, @PathVariable("id") Long id) {
        cartService.deleteItem(userDto.getId(), id);
        return success();
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

