package shop.bookbom.shop.domain.pointrate.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : shop.bookbom.shop.domain.pointrate.dto
 * fileName       : PointRateSimpleInformation
 * author         : 전석준
 * date           : 2024-04-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-12        전석준       최초 생성
 */
@Getter
@NoArgsConstructor
public class PointRateSimpleInformation {
    private String earnType;
    private Integer earnPoint;

    @Builder
    public PointRateSimpleInformation(String earnType, Integer earnPoint) {
        this.earnType = earnType;
        this.earnPoint = earnPoint;
    }
}
