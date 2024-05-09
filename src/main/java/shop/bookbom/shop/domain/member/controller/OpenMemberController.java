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

    /**
     * 회원가입을 처리하는 메서드입니다.
     *
     * @param signUpRequest 회원가입 요청 정보
     */
    @PostMapping("/sign-up")
    public CommonResponse<Void> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        memberService.save(signUpRequest);
        return CommonResponse.success();
    }
}
