package shop.bookbom.shop.domain.pointrate.dto;

import lombok.Builder;
import lombok.Getter;
import shop.bookbom.shop.domain.pointrate.entity.PointRate;

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
public class PointRateSimpleInformation {
    private String earnType;
    private int earnPoint;

    @Builder
    public PointRateSimpleInformation(PointRate pointRate) {
        this.earnType = pointRate.getEarnType().getValue();
        this.earnPoint = pointRate.getEarnPoint();
    }
}
