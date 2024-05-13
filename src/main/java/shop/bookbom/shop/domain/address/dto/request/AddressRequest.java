package shop.bookbom.shop.domain.address.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddressRequest {
    private String nickname;
    @Size(min = 5, max = 5, message = "우편번호는 5자리여야 합니다.")
    private String zipcode;
    @NotEmpty(message = "주소를 입력해주세요.")
    private String address;
    @NotEmpty(message = "상세 주소를 입력해주세요.")
    private String addressDetail;
}
