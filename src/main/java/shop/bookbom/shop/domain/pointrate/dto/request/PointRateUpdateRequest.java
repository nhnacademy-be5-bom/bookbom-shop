package shop.bookbom.shop.domain.pointrate.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PointRateUpdateRequest {
    @NotBlank(message = "적립 유형은 필수로 입력되어야 합니다.")
    private String earnType;
    @Min(value = 1, message = "적립률은 0 이상이어야 합니다.")
    private int earnPoint;
}
