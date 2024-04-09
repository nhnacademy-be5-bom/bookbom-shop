package shop.bookbom.shop.domain.order.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import shop.bookbom.shop.domain.wrapper.dto.response.WrapperResponse;

@Getter
public class BeforeOrderResponse {
    private int TotalOrderCount;
    private List<BeforeOrderBookResponse> beforeOrderBookResponseList;

    private List<WrapperResponse> wrapperList;

    @Builder
    public BeforeOrderResponse(int totalOrderCount, List<BeforeOrderBookResponse> beforeOrderBookResponseList,
                               List<WrapperResponse> wrapperList) {
        TotalOrderCount = totalOrderCount;
        this.beforeOrderBookResponseList = beforeOrderBookResponseList;
        this.wrapperList = wrapperList;
    }
}
