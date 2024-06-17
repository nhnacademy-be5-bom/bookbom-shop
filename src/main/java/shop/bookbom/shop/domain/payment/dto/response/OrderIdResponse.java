package shop.bookbom.shop.domain.payment.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class OrderIdResponse {
    private Long orderId;

    public OrderIdResponse(Long orderId) {
        this.orderId = orderId;
    }
}
