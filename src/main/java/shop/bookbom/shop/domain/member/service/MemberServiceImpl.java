package shop.bookbom.shop.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.bookbom.shop.domain.member.dto.request.MemberRequestDto;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.repository.MemberRepository;

@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public void save(MemberRequestDto memberRequestDto) {
        Member member = Member.builder()
                .id(memberRequestDto.getId())
                .name(memberRequestDto.getName())
                .nickname(memberRequestDto.getNickname())
                .phoneNumber(memberRequestDto.getPhoneNumber())
                .birthDate(memberRequestDto.getBirthDate())
                .point(0) // point_rate 테이블의 가입적립포인트를 가져오는 코드로 수정 필요
                .build();

        memberRepository.save(member);
    }
}
