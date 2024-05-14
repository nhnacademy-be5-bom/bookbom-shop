package shop.bookbom.shop.common.controller;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.annotation.Login;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.common.dto.response.UserCheckResponse;
import shop.bookbom.shop.domain.users.dto.UserDto;
import shop.bookbom.shop.domain.users.entity.User;
import shop.bookbom.shop.domain.users.repository.UserRepository;

@RestController
@RequestMapping("/shop/open")
@RequiredArgsConstructor
public class UserCheckController {
    private final UserRepository userRepository;

    @GetMapping("/check-user")
    public CommonResponse<UserCheckResponse> checkUser(@Login UserDto userDto) {
        boolean check = true;
        if (userDto == null) {
            check = false;
        } else {
            Optional<User> user = userRepository.findById(userDto.getId());
            if (user.isEmpty()) {
                check = false;
            }
        }
        return CommonResponse.successWithData(new UserCheckResponse(check));
    }
}
