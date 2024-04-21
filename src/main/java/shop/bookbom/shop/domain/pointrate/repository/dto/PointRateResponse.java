package shop.bookbom.shop.domain.pointrate.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import shop.bookbom.shop.domain.pointrate.entity.EarnPointType;

@Getter
public class PointRateResponse {
    private Long id;
    private String name;
    private EarnPointType earnType;
    private int earnPoint;

    @QueryProjection
    public PointRateResponse(Long id, String name, EarnPointType earnType, int earnPoint) {
        this.id = id;
        this.name = name;
        this.earnType = earnType;
        this.earnPoint = earnPoint;
    }
}
