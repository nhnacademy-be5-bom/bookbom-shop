package shop.bookbom.shop.domain.pointhistory.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PointHistoryResponse {
    private Long id;
    private String reason;
    private String detail;
    private int changePoint;
    private LocalDateTime changeDate;

    @Builder
    @QueryProjection
    public PointHistoryResponse(
            Long id,
            String reason,
            String detail,
            int changePoint,
            LocalDateTime changeDate
    ) {
        this.id = id;
        this.reason = reason;
        this.detail = detail;
        this.changePoint = changePoint;
        this.changeDate = changeDate;
    }
}
