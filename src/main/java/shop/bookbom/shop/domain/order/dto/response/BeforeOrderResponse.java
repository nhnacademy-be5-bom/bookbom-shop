package shop.bookbom.shop.domain.order.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import shop.bookbom.shop.domain.wrapper.dto.WrapperDto;

@Getter
public class BeforeOrderResponse {
    private int totalOrderCount;
    private List<BeforeOrderBookResponse> beforeOrderBookResponseList;
    private List<WrapperDto> wrapperList;

    @Builder
    public BeforeOrderResponse(int totalOrderCount, List<BeforeOrderBookResponse> beforeOrderBookResponseList,
                               List<WrapperDto> wrapperList) {
        this.totalOrderCount = totalOrderCount;
        this.beforeOrderBookResponseList = beforeOrderBookResponseList;
        this.wrapperList = wrapperList;
    }
}
