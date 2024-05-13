package shop.bookbom.shop.domain.address.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.annotation.Login;
import shop.bookbom.shop.common.CommonListResponse;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.address.dto.request.AddressRequest;
import shop.bookbom.shop.domain.address.dto.response.AddressResponse;
import shop.bookbom.shop.domain.address.exception.AddressAlreadyExistsException;
import shop.bookbom.shop.domain.address.service.AddressServiceImpl;
import shop.bookbom.shop.domain.users.dto.UserDto;

@RestController
@RequestMapping("/shop/users/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressServiceImpl addressService;

    @GetMapping
    public CommonListResponse<AddressResponse> getAddressBook(@Login UserDto userDto) {
        List<AddressResponse> addressBook = addressService.getAddressBook(userDto.getId());
        return CommonListResponse.successWithList(addressBook);
    }

    @PostMapping
    public CommonResponse<Void> addAddress(@Login UserDto userDto, @Valid @RequestBody AddressRequest request) {
        validSameAddress(userDto.getId(), request);
        addressService.saveAddress(userDto.getId(), request.getNickname(), request.getZipcode(), request.getAddress(),
                request.getAddressDetail());
        return CommonResponse.success();
    }

    @PostMapping("/default/{addressId}")
    public CommonResponse<Void> updateDefaultAddress(@Login UserDto userDto, @PathVariable("addressId") Long id) {
        addressService.updateDefaultAddress(userDto.getId(), id);
        return CommonResponse.success();
    }

    @PutMapping("/{addressId}")
    public CommonResponse<Void> updateAddress(
            @Login UserDto userDto,
            @PathVariable("addressId") Long id,
            @Valid @RequestBody AddressRequest request
    ) {
        validSameAddress(userDto.getId(), request);
        addressService.updateAddress(id, request.getNickname(), request.getZipcode(), request.getAddress(),
                request.getAddressDetail());
        return CommonResponse.success();
    }

    /**
     * 동일한 주소가 있는지 확인하는 메서드입니다.
     *
     * @param id      주소 ID
     * @param request 주소 요청
     */
    private void validSameAddress(Long id, AddressRequest request) {
        if (addressService.existsSameAddress(id, request.getZipcode(), request.getAddress(),
                request.getAddressDetail())) {
            throw new AddressAlreadyExistsException();
        }
    }
}
