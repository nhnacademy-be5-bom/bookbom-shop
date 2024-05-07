package shop.bookbom.shop.domain.order.dto.request;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderStatusUpdateRequest {
    @NotNull(message = "주문 ID를 입력해주세요.")
    private List<Long> orderIds;
    @NotEmpty(message = "주문 상태를 입력해주세요.")
    private String status;
}
