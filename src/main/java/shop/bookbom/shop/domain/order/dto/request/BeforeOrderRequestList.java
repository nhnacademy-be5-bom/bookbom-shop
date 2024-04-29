package shop.bookbom.shop.domain.order.dto.request;

import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BeforeOrderRequestList {
    @Valid
    private List<BeforeOrderRequest> beforeOrderRequests;
}
