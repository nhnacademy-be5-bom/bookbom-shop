package shop.bookbom.shop.domain.address.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddressRequest {
    private String nickName;

    @NotEmpty
    @Max(5)
    private String zipCode;

    @NotEmpty
    private String address;

    @NotEmpty
    private String addressDetail;

    @NotEmpty
    private boolean defaultAddress;

    @NotEmpty
    private long userId;
}
