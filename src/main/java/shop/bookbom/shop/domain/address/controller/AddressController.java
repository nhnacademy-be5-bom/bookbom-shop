package shop.bookbom.shop.domain.address.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.annotation.Login;
import shop.bookbom.shop.common.CommonListResponse;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.address.dto.request.AddressSaveRequest;
import shop.bookbom.shop.domain.address.dto.response.AddressResponse;
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
    public CommonResponse<Void> addAddress(@Login UserDto userDto, @Valid @RequestBody AddressSaveRequest request) {
        addressService.saveAddress(userDto.getId(), request.getNickname(), request.getZipcode(), request.getAddress(),
                request.getAddressDetail());
        return CommonResponse.success();
    }
}
