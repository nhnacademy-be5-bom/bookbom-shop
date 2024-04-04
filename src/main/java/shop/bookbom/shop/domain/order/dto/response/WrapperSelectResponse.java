package shop.bookbom.shop.domain.order.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectRequest;

@Getter
@Setter
public class WrapperSelectResponse {
    private int totalCount;
    private List<BeforeOrderBookResponse> beforeOrderBookResponses;
    private List<WrapperSelectRequest> wrapperSelectRequestList;

}
