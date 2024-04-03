package shop.bookbom.shop.domain.order.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import shop.bookbom.shop.domain.wrapper.entity.Wrapper;

@Getter
@Setter
public class BeforeOrderResponse {
    private int TotalOrderCount;
    private List<BeforeOrderBookResponse> beforeOrderBookResponses;

    private List<Wrapper> wrapperList;
}
