package shop.bookbom.shop.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.annotation.Login;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.member.dto.response.MemberInfoResponse;
import shop.bookbom.shop.domain.member.service.MemberService;
import shop.bookbom.shop.domain.users.dto.UserDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/users/my-page")
    public CommonResponse<MemberInfoResponse> myPage(@Login UserDto userDto) {
        return CommonResponse.successWithData(memberService.getMemberInfo(userDto.getId()));
    }
}
