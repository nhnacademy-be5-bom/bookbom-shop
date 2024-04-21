package shop.bookbom.shop.domain.member.service;

import shop.bookbom.shop.domain.member.dto.request.MemberRequestDto;

public interface MemberService {
    void save(MemberRequestDto memberRequestDto);
}
