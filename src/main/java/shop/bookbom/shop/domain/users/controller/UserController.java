package shop.bookbom.shop.domain.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.users.dto.request.ResetPasswordRequestDto;
import shop.bookbom.shop.domain.users.dto.request.UserRequestDto;
import shop.bookbom.shop.domain.users.service.UserService;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // #1 CREATE USER
    // request : String email, String password, String roleName
    @PostMapping("/user")
    public CommonResponse<Long> registerUser(@RequestBody UserRequestDto userRequestDto) {
        Long userId = userService.save(userRequestDto);
        return CommonResponse.successWithData(userId);
    }

    // #2-1 UPDATE USER - 비밀번호 변경
    // request : Long id, String password
    @PatchMapping("/{id}/password")
    public CommonResponse resetPassword(@RequestBody ResetPasswordRequestDto resetPasswordRequestDto) {
        userService.resetPassword(resetPasswordRequestDto);
        return CommonResponse.success();
    }

}
