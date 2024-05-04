package shop.bookbom.shop.domain.order.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponse {
    private String orderName;
    private String orderId;
    private Integer amount;
    
}
