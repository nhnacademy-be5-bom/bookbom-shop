package shop.bookbom.shop.domain.member.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;
import shop.bookbom.shop.domain.member.dto.request.MemberRequestDto;
import shop.bookbom.shop.domain.member.service.MemberService;

@RestController
@RequestMapping("/shop/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * CREATE MEMBER
     *
     * @param memberRequestDto : Long id, String name, String phoneNumber,
     *                         LocalDate birthDate, String nickname
     */
    @PostMapping
    public CommonResponse registerMember(@RequestBody @Valid MemberRequestDto memberRequestDto,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BaseException(ErrorCode.COMMON_INVALID_PARAMETER);
        }

        memberService.save(memberRequestDto);
        return CommonResponse.success();
    }
}
