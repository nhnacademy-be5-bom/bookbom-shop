package shop.bookbom.shop.domain.order.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import shop.bookbom.shop.domain.wrapper.entity.Wrapper;

@Getter
public class BeforeOrderResponse {
    private int TotalOrderCount;
    private List<BeforeOrderBookResponse> beforeOrderBookResponseList;

    private List<Wrapper> wrapperList;

    @Builder
    public BeforeOrderResponse(int totalOrderCount, List<BeforeOrderBookResponse> beforeOrderBookResponseList,
                               List<Wrapper> wrapperList) {
        TotalOrderCount = totalOrderCount;
        this.beforeOrderBookResponseList = beforeOrderBookResponseList;
        this.wrapperList = wrapperList;
    }
}
