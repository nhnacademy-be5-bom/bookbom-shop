package shop.bookbom.shop.domain.wish.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonListResponse;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.cart.dto.request.CartAddRequest;
import shop.bookbom.shop.domain.wish.dto.request.WishAddDeleteRequest;
import shop.bookbom.shop.domain.wish.dto.response.WishInfoResponse;
import shop.bookbom.shop.domain.wish.dto.response.WishTotalCountResponse;
import shop.bookbom.shop.domain.wish.exception.WishInvalidRequestException;
import shop.bookbom.shop.domain.wish.service.WishService;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class WishController {
    private final WishService wishService;

    /**
     * 찜 목록에 도서를 추가합니다
     *
     * @param userId
     * @param request
     * @return
     */
    @PostMapping("/wish/{id}")
    public CommonResponse<Void> addWish(@PathVariable("id") @Valid Long userId,
                                        @RequestBody List<WishAddDeleteRequest> request) {
        if (!isValidRequest(request)) {
            throw new WishInvalidRequestException();
        }
        wishService.addWish(request, userId);
        return CommonResponse.success();
    }

    /**
     * 찜 목록에 있는 도서를 삭제합니다
     *
     * @param userId
     * @param request
     * @return
     */
    @DeleteMapping("/wish/{id}")
    public CommonResponse<Void> deleteWish(@PathVariable("id") Long userId,
                                           @RequestBody List<WishAddDeleteRequest> request) {
        if (!isValidRequest(request)) {
            throw new WishInvalidRequestException();
        }
        wishService.deleteWish(request, userId);
        return CommonResponse.success();
    }

    /**
     * 회원의 찜 목록을 조회합니다
     *
     * @param userId
     * @return
     */
    @GetMapping("/wish/{id}")
    public CommonListResponse<WishInfoResponse> getWish(@PathVariable("id") Long userId) {
        return CommonListResponse.successWithList(wishService.getWishInfo(userId));
    }

    /**
     * 회원의 모든 찜 개수를 조회합니다
     *
     * @param userId
     * @return
     */
    @GetMapping("/wish/count/{id}")
    public CommonResponse<WishTotalCountResponse> getWishCount(@PathVariable("id") Long userId) {
        return CommonResponse.successWithData(wishService.getWishTotalCount(userId));
    }

    /**
     * request 유효성 검사
     *
     * @param requests
     * @return
     */
    private boolean isValidRequest(List<WishAddDeleteRequest> requests) {
        return requests.stream()
                .allMatch(request -> request.getBookId() != null
                        && request.getBookId() >= 1);
    }
}
