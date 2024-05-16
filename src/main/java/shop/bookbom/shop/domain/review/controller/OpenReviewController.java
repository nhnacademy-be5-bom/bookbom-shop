package shop.bookbom.shop.domain.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.annotation.Login;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.review.dto.response.ReviewCheckResponse;
import shop.bookbom.shop.domain.review.dto.response.ReviewResponse;
import shop.bookbom.shop.domain.review.service.ReviewService;
import shop.bookbom.shop.domain.users.dto.UserDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop/open/reviews")
public class OpenReviewController {
    private final ReviewService reviewService;

    @GetMapping("/exists-check")
    public CommonResponse<ReviewCheckResponse> existsCheck(
            @Login UserDto userDto,
            @RequestParam("bookId") Long bookId,
            @RequestParam("orderId") Long orderId
    ) {
        return CommonResponse.successWithData(reviewService.existsCheck(userDto.getId(), bookId, orderId));
    }

    @GetMapping
    public CommonResponse<Page<ReviewResponse>> getReviews(
            @RequestParam("bookId") Long bookId,
            @PageableDefault Pageable pageable
    ) {
        return CommonResponse.successWithData(reviewService.getReviews(bookId, pageable));
    }
}
