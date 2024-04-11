package shop.bookbom.shop.domain.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.users.dto.request.ChangeRegisteredRequestDto;
import shop.bookbom.shop.domain.users.dto.request.ResetPasswordRequestDto;
import shop.bookbom.shop.domain.users.dto.request.UserRequestDto;
import shop.bookbom.shop.domain.users.service.UserService;

@RestController
@RequestMapping("/shop/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // #1 CREATE USER
    // request : String email, String password, String roleName
    @PostMapping("")
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

    // #2-2 UPDATE USER - REGISTERED 설정
    // request : Long id, boolean registered
    @PatchMapping("/{id}/registered")
    public CommonResponse changeRegistered(@RequestBody ChangeRegisteredRequestDto changeRegisteredRequestDto) {
        userService.changeRegistered(changeRegisteredRequestDto);
        return CommonResponse.success();
    }

    // #3-1 READ USER - Id로 registered 읽어옴
    @GetMapping("/{id}/registered")
    public CommonResponse<Boolean> getRegistered(@PathVariable Long id) {
        boolean registered = userService.isRegistered(id);
        return CommonResponse.successWithData(Boolean.valueOf(registered));
    }


    // #3-2 READ USER - Email이 사용 가능한지 확인
    @GetMapping("/{email}")
    public CommonResponse<Boolean> checkEmailCanUse(@PathVariable String email) {
        if (userService.checkEmailCanUse(email)) {
            return CommonResponse.successWithData(Boolean.TRUE);
        } else {
            return CommonResponse.successWithData(Boolean.FALSE);
        }
    }

}
