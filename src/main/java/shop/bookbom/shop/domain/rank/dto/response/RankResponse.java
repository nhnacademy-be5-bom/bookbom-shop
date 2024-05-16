package shop.bookbom.shop.domain.rank.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.pointrate.entity.EarnPointType;

@Getter
@NoArgsConstructor
public class RankResponse {
    private String name;
    private int earnPoint;
    private EarnPointType earnType;

    @Builder
    public RankResponse(String name, int earnPoint, EarnPointType earnType) {
        this.name = name;
        this.earnPoint = earnPoint;
        this.earnType = earnType;
    }
}
