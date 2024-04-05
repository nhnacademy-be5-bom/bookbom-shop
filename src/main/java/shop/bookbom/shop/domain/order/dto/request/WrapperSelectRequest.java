package shop.bookbom.shop.domain.order.dto.request;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WrapperSelectRequest {
    @NotNull
    private List<WrapperSelectBookRequest> wrapperSelectBookRequestList;
    @Min(value = 1, message = "총 주문 수는 1개 이상이어야합니다.")
    private int totalOrderCount;

    public WrapperSelectRequest(List<WrapperSelectBookRequest> wrapperSelectBookRequestList, int totalOrderCount) {
        this.wrapperSelectBookRequestList = wrapperSelectBookRequestList;
        totalOrderCount = totalOrderCount;
    }
}
