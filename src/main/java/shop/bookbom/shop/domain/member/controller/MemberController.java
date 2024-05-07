package shop.bookbom.shop.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.member.dto.response.MemberInfoResponse;
import shop.bookbom.shop.domain.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/my-page")
    public CommonResponse<MemberInfoResponse> myPage(@RequestParam("userId") Long id) {
        return CommonResponse.successWithData(memberService.getMemberInfo(id));
    }
}
