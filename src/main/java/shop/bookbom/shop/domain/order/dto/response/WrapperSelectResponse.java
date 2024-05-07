package shop.bookbom.shop.domain.order.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class WrapperSelectResponse {
    private int totalOrderCount;
    private List<WrapperSelectBookResponse> wrapperSelectResponseList;
    private List<String> estimatedDateList;
    private int deliveryCost;
    private int WrapCost;

    @Builder
    public WrapperSelectResponse(int totalOrderCount, List<WrapperSelectBookResponse> wrapperSelectResponseList,
                                 List<String> estimatedDateList, int deliveryCost, int wrapCost) {
        this.totalOrderCount = totalOrderCount;
        this.wrapperSelectResponseList = wrapperSelectResponseList;
        this.estimatedDateList = estimatedDateList;
        this.deliveryCost = deliveryCost;
        WrapCost = wrapCost;
    }
}
