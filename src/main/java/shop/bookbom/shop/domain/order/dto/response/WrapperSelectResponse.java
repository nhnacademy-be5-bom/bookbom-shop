package shop.bookbom.shop.domain.order.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectBookRequest;

@Getter
public class WrapperSelectResponse {
    private int totalOrderCount;
    private Long userId;
    private List<WrapperSelectBookRequest> wrapperSelectRequestList;

    @Builder

    public WrapperSelectResponse(int totalOrderCount, Long userId,
                                 List<WrapperSelectBookRequest> wrapperSelectRequestList) {
        this.totalOrderCount = totalOrderCount;
        this.userId = userId;
        this.wrapperSelectRequestList = wrapperSelectRequestList;
    }
}
