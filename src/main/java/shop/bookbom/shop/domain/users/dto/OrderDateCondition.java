package shop.bookbom.shop.domain.users.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderDateCondition {
    private LocalDate orderDateMin;
    private LocalDate orderDateMax;
}
