package shop.bookbom.shop.domain.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.common.exception.InvalidParameterException;
import shop.bookbom.shop.domain.users.dto.response.EmailCheckResponse;
import shop.bookbom.shop.domain.users.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop/open")
public class OpenUserController {
    private final UserService userService;

    @GetMapping("/users/check-email")
    public CommonResponse<EmailCheckResponse> checkEmail(
            @RequestParam(value = "email", required = false) String email
    ) {
        if (email == null || email.isEmpty()) {
            throw new InvalidParameterException("이메일을 입력해주세요.");
        }
        boolean canUse = userService.checkEmailCanUse(email);
        return CommonResponse.successWithData(new EmailCheckResponse(canUse));
    }
}
