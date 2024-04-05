package shop.bookbom.shop.domain.order.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectBookRequest;

@Getter
public class WrapperSelectResponse {
    private int totalCount;
    private Long userId;
    private List<WrapperSelectBookRequest> wrapperSelectRequestList;

    @Builder

    public WrapperSelectResponse(int totalCount, Long userId,
                                 List<WrapperSelectBookRequest> wrapperSelectRequestList) {
        this.totalCount = totalCount;
        this.userId = userId;
        this.wrapperSelectRequestList = wrapperSelectRequestList;
    }
}
