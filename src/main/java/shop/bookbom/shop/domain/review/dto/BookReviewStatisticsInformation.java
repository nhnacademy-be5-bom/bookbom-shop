package shop.bookbom.shop.domain.review.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : shop.bookbom.shop.domain.review.dto
 * fileName       : BookReviewAverageInformation
 * author         : UuLaptop
 * date           : 2024-04-16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-16        UuLaptop       최초 생성
 */
@Getter
@NoArgsConstructor
public class BookReviewStatisticsInformation {
    private Integer totalReviewCount;
    private Double averageReviewRate;

    @Builder
    @QueryProjection
    public BookReviewStatisticsInformation(Integer totalReviewCount, Double averageReviewRate) {
        this.totalReviewCount = totalReviewCount;
        this.averageReviewRate = averageReviewRate;
    }
}
