package shop.bookbom.shop.domain.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : shop.bookbom.shop.domain.review.dto
 * fileName       : ReviewSimpleInformation
 * author         : UuLaptop
 * date           : 2024-04-15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-15        UuLaptop       최초 생성
 */
@Getter
@NoArgsConstructor
public class ReviewSimpleInformation {
    private Long totalCount;
    private Integer averageRating;

    @Builder
    public ReviewSimpleInformation(Long totalCount, Integer averageRating) {
        this.totalCount = totalCount;
        this.averageRating = averageRating;
    }
}
