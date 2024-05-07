package shop.bookbom.shop.domain.order.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderListRequest {
    private String dateFrom;
    private String dateTo;
    @NotBlank(message = "정렬 기준을 선택해주세요.")
    private String sort;
    @NotBlank(message = "주문 상태를 선택해주세요.")
    private String deliveryStatus;
}
