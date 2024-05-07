package shop.bookbom.shop.domain.order.dto.request;

import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WrapperSelectRequest {
    @Valid
    private List<WrapperSelectBookRequest> wrapperSelectBookRequestList;

}
