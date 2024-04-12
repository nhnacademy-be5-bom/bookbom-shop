package shop.bookbom.shop.domain.wish.controller;

import java.util.List;
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
import shop.bookbom.shop.domain.wish.dto.request.WishAddRequest;
import shop.bookbom.shop.domain.wish.dto.response.WishInfoResponse;
import shop.bookbom.shop.domain.wish.dto.response.WishTotalCountResponse;
import shop.bookbom.shop.domain.wish.service.WishService;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class WishController {
    private final WishService wishService;

    @PostMapping("/wish/{id}")
    public CommonResponse<Void> addWish(@PathVariable("id") Long userId, @RequestBody List<WishAddRequest> request) {
        return CommonResponse.success();
    }

    @DeleteMapping("/wish/{id}")
    public CommonResponse<Void> deleteWish() {
        return CommonResponse.success();
    }

    @GetMapping("/wish/{id}")
    public CommonListResponse<WishInfoResponse> getWish(@PathVariable("id") Long userId) {
        return CommonListResponse.successWithList(wishService.getWishInfo(userId));
    }

    @GetMapping("/wish/count/{id}")
    public CommonResponse<WishTotalCountResponse> getWishCount(@PathVariable("id") Long userId){
        return CommonResponse.successWithData(wishService.getWishTotalCount(userId));
    }
}
