package shop.bookbom.shop.domain.order.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeforeOrderRequest {

    private List<BeforeOrderBookRequest> beforeOrderBookRequests;


}
