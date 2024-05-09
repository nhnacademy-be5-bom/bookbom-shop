package shop.bookbom.shop.domain.member.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.member.dto.request.SignUpRequest;
import shop.bookbom.shop.domain.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop/open")
public class OpenMemberController {
    private final MemberService memberService;

    @PostMapping("/sign-up")
    public CommonResponse<Long> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        Long userId = memberService.save(signUpRequest);
        return CommonResponse.successWithData(userId);
    }
}
