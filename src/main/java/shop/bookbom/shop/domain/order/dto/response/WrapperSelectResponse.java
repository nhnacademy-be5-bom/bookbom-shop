package shop.bookbom.shop.domain.order.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class WrapperSelectResponse {
    private int totalOrderCount;
    private Long userId;
    private List<WrapperSelectBookResponse> wrapperSelectResponseList;

    @Builder
    public WrapperSelectResponse(int totalOrderCount, Long userId,
                                 List<WrapperSelectBookResponse> wrapperSelectResponseList) {
        this.totalOrderCount = totalOrderCount;
        this.userId = userId;
        this.wrapperSelectResponseList = wrapperSelectResponseList;
    }
}
