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
    private Long id;
    private int rate;
    private String content;

    @Builder
    public ReviewSimpleInformation(Long id, int rate, String content) {
        this.id = id;
        this.rate = rate;
        this.content = content;
    }
}
