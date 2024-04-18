package shop.bookbom.shop.domain.order.dto.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WrapperSelectRequest {
    @Valid
    private List<WrapperSelectBookRequest> wrapperSelectBookRequestList;
    @Min(value = 1, message = "총 주문 수는 1개 이상이어야합니다.")
    private int totalOrderCount;

}
