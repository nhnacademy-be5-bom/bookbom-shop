package shop.bookbom.shop.domain.member.dto.request;

import java.util.List;
import lombok.Getter;

@Getter
public class WithDrawDTO {
    private List<String> reasons;
}
