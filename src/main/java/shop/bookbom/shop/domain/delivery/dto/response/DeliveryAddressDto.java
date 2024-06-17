package shop.bookbom.shop.domain.delivery.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.bookbom.shop.domain.deliveryaddress.entity.DeliveryAddress;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeliveryAddressDto {
    private Long id;
    private String zipCode;
    private String address;
    private String detailAddress;

    public static DeliveryAddressDto of(DeliveryAddress deliveryAddress) {
        return new DeliveryAddressDto(
                deliveryAddress.getId(),
                deliveryAddress.getZipCode(),
                deliveryAddress.getDeliveryAddress(),
                deliveryAddress.getAddressDetail()
        );
    }
}
