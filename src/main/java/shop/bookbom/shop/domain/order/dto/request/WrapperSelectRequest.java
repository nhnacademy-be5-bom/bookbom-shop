package shop.bookbom.shop.domain.order.dto.request;

import java.util.List;
import lombok.Data;

@Data
public class WrapperSelectRequest {
    private List<WrapperSelectBookRequest> wrapperSelectBookRequestList;
    private int TotalOrderCount;

    public WrapperSelectRequest(List<WrapperSelectBookRequest> wrapperSelectBookRequestList, int totalOrderCount) {
        this.wrapperSelectBookRequestList = wrapperSelectBookRequestList;
        TotalOrderCount = totalOrderCount;
    }
}
