package shop.bookbom.shop.domain.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.common.exception.InvalidParameterException;
import shop.bookbom.shop.domain.member.service.MemberService;
import shop.bookbom.shop.domain.users.dto.response.SignupCheckResponse;
import shop.bookbom.shop.domain.users.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop/open")
public class OpenUserController {
    private final UserService userService;
    private final MemberService memberService;

    @GetMapping("/users/check-email")
    public CommonResponse<SignupCheckResponse> checkEmail(
            @RequestParam(value = "email", required = false) String email
    ) {
        if (email == null || email.isEmpty()) {
            throw new InvalidParameterException("이메일을 입력해주세요.");
        }
        boolean canUse = userService.checkEmailCanUse(email);
        return CommonResponse.successWithData(new SignupCheckResponse(canUse));
    }

    @GetMapping("/users/check-nickname")
    public CommonResponse<SignupCheckResponse> checkNickname(
            @RequestParam(value = "nickname", required = false) String nickname
    ) {
        if (nickname == null || nickname.isEmpty()) {
            throw new InvalidParameterException("닉네임을 입력해주세요.");
        }
        boolean canUse = memberService.checkNicknameCanUse(nickname);
        return CommonResponse.successWithData(new SignupCheckResponse(canUse));
    }
}
