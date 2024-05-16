package shop.bookbom.shop.domain.review.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class ReviewRequest {
    @NotNull(message = "책 ID는 필수로 입력해주세야 합니다.")
    private Long bookId;
    @NotNull(message = "주문 ID는 필수로 입력해주세야 합니다.")
    private Long orderId;
    @NotEmpty(message = "리뷰 타입은 필수로 입력해주세야 합니다.")
    private String type;
    @Min(value = 1, message = "별점은 필수로 입력해주세야 합니다.")
    private int rating;
    @NotEmpty(message = "리뷰 내용은 필수로 입력해주세야 합니다.")
    private String content;
    private MultipartFile image;
}
