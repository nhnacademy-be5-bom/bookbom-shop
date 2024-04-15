package shop.bookbom.shop.domain.publisher.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * packageName    : shop.bookbom.shop.domain.publisher.dto
 * fileName       : PublisherSimpleInformation
 * author         : 전석준
 * date           : 2024-04-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-12        전석준       최초 생성
 */
@Getter
public class PublisherSimpleInformation {
    private String name;

    @Builder
    public PublisherSimpleInformation(String name) {
        this.name = name;
    }
}
