package shop.bookbom.shop.domain.address.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.annotation.Login;
import shop.bookbom.shop.common.CommonListResponse;
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
}
