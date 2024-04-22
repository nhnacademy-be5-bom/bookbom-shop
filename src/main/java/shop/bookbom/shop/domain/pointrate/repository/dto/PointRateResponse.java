package shop.bookbom.shop.domain.pointrate.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import shop.bookbom.shop.domain.pointrate.entity.EarnPointType;
import shop.bookbom.shop.domain.pointrate.entity.PointRate;

@Getter
public class PointRateResponse {
    private Long id;
    private String name;
    private EarnPointType earnType;
    private int earnPoint;

    @Builder
    @QueryProjection
    public PointRateResponse(Long id, String name, EarnPointType earnType, int earnPoint) {
        this.id = id;
        this.name = name;
        this.earnType = earnType;
        this.earnPoint = earnPoint;
    }

    public static PointRateResponse from(PointRate pointRate) {
        return PointRateResponse.builder()
                .id(pointRate.getId())
                .name(pointRate.getName())
                .earnType(pointRate.getEarnType())
                .earnPoint(pointRate.getEarnPoint())
                .build();
    }
}
