package shop.bookbom.shop.domain.address.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.bookbom.shop.domain.address.entity.Address;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressResponse {
    private Long id;
    private String nickname;
    private String zipCode;
    private String address;
    private String addressDetail;
    private boolean defaultAddress;

    public static AddressResponse from(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getNickname(),
                address.getZipCode(),
                address.getAddress(),
                address.getAddressDetail(),
                address.isDefaultAddress()
        );
    }
}
