package shop.bookbom.shop.domain.review.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.bookbom.shop.domain.review.entity.Review;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewResponse {
    private Long id;
    private String nickname;
    private LocalDateTime createdAt;
    private List<String> images;
    private String content;
    private int rate;

    public static ReviewResponse from(Review review) {
        List<String> reviewImages = review.getReviewImages().stream()
                .map(r -> r.getFile().getUrl())
                .collect(Collectors.toList());
        return new ReviewResponse(
                review.getId(),
                review.getMember().getNickname(),
                review.getCreatedAt(),
                reviewImages,
                review.getContent(),
                review.getRate());
    }
}
