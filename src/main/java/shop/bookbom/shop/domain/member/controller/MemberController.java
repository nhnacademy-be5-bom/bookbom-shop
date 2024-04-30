package shop.bookbom.shop.domain.member.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;
import shop.bookbom.shop.domain.member.dto.request.MemberRequestDto;
import shop.bookbom.shop.domain.member.dto.request.SignUpFormDto;
import shop.bookbom.shop.domain.member.dto.response.MemberInfoResponse;
import shop.bookbom.shop.domain.member.service.MemberService;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * CREATE CUSTOM MEMBER
     *
     * @param memberRequestDto : Long id, String name, String phoneNumber,
     *                         LocalDate birthDate, String nickname
     */
    @PostMapping("/members")
    public CommonResponse registerMember(@RequestBody @Valid MemberRequestDto memberRequestDto,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BaseException(ErrorCode.COMMON_INVALID_PARAMETER);
        }

        memberService.save(memberRequestDto);
        return CommonResponse.success();
    }

    /**
     * CREATE MEMBER BY SIGNUP
     * 회원가입을 통한 member 생성
     *
     * @param signUpFormDto
     * @return
     */
    @PostMapping("/signup")
    public CommonResponse<Long> registerMemberBySignUp(@RequestBody @Valid SignUpFormDto signUpFormDto,
                                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BaseException(ErrorCode.COMMON_INVALID_PARAMETER);
        }

        Long userId = memberService.signUp(signUpFormDto);
        return CommonResponse.successWithData(userId);
    }

    @GetMapping("/members/my-page")
    public CommonResponse<MemberInfoResponse> myPage(@RequestParam("userId") Long id) {
        return CommonResponse.successWithData(memberService.getMemberInfo(id));
    }

}
