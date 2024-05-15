package shop.bookbom.shop.domain.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import shop.bookbom.shop.annotation.Login;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.common.exception.InvalidParameterException;
import shop.bookbom.shop.domain.review.dto.response.ReviewCheckResponse;
import shop.bookbom.shop.domain.review.service.ReviewService;
import shop.bookbom.shop.domain.users.dto.UserDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public CommonResponse<Void> createReview(
            @Login UserDto userDto,
            @RequestParam("bookId") Long bookId,
            @RequestParam("orderId") Long orderId,
            @RequestParam("type") String type,
            @RequestParam("rating") int rating,
            @RequestParam("content") String content,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        if (type.equals("photo") && image == null) {
            throw new InvalidParameterException("사진 리뷰는 이미지를 필수로 입력해주셔야 합니다.");
        }
        reviewService.createReview(userDto.getId(), bookId, orderId, type, rating, content, image);
        return CommonResponse.success();
    }

    @GetMapping("/exists-check")
    public CommonResponse<ReviewCheckResponse> existsCheck(
            @Login UserDto userDto,
            @RequestParam("bookId") Long bookId,
            @RequestParam("orderId") Long orderId
    ) {
        return CommonResponse.successWithData(reviewService.existsCheck(userDto.getId(), bookId, orderId));
    }
}
