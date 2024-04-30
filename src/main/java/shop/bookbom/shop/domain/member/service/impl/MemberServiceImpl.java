package shop.bookbom.shop.domain.member.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.member.dto.request.MemberRequestDto;
import shop.bookbom.shop.domain.member.dto.request.SignUpFormDto;
import shop.bookbom.shop.domain.member.dto.response.MemberInfoResponse;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.entity.MemberStatus;
import shop.bookbom.shop.domain.member.repository.MemberRepository;
import shop.bookbom.shop.domain.member.service.MemberService;
import shop.bookbom.shop.domain.rank.entity.Rank;
import shop.bookbom.shop.domain.rank.exception.RankNotFoundException;
import shop.bookbom.shop.domain.rank.repository.RankRepository;
import shop.bookbom.shop.domain.role.entity.Role;
import shop.bookbom.shop.domain.role.repository.RoleRepository;
import shop.bookbom.shop.domain.users.exception.RoleNotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final RankRepository rankRepository;

    /**
     * 커스텀된 사용자를 만들거나
     * 비회원을 회원 전환할 때 사용하는 method
     * role rank status를 지정할 수 있다.
     *
     * @param memberRequestDto
     */
    @Override
    @Transactional
    public void save(MemberRequestDto memberRequestDto) {
        log.info("role 검색");
        Role role = roleRepository.findByName(memberRequestDto.getRoleName()).orElseThrow(
                RoleNotFoundException::new
        );

        log.info("rank 검색");
        Rank rank = rankRepository.findByName(memberRequestDto.getRankName()).orElseThrow(
                RankNotFoundException::new
        );
        Member member = Member.builder()
                .id(memberRequestDto.getId())
                .name(memberRequestDto.getName())
                .nickname(memberRequestDto.getNickname())
                .phoneNumber(memberRequestDto.getPhoneNumber())
                .birthDate(memberRequestDto.getBirthDate())
                .point(0) // point_rate 테이블의 가입적립포인트를 가져오는 코드로 수정 필요
                .status(MemberStatus.valueOf(memberRequestDto.getStatus()))
                .role(role)
                .rank(rank)
                .build();

        memberRepository.save(member);

        // TODO #2 EXCEPTION 경우의 수에 대한 TEST CODE 추가
    }

    /**
     * 회원가입시 사용하는 method로
     * role과 rank, status가 정해져있다.
     *
     * @param signUpFormDto
     * @return userId
     */
    @Override
    @Transactional
    public Long signUp(SignUpFormDto signUpFormDto) {
        log.info("role 검색");
        Role role = roleRepository.findByName("ROLE_UESR").orElseThrow(
                RoleNotFoundException::new
        );

        log.info("rank 검색");
        Rank rank = rankRepository.findByName("STANDARD").orElseThrow(
                RankNotFoundException::new
        );

        log.info("role 검색 완료");
        Member member = Member.builder()
                .email(signUpFormDto.getEmail())
                .password(signUpFormDto.getPassword())
                .role(role)
                .name(signUpFormDto.getName())
                .nickname(signUpFormDto.getNickname())
                .phoneNumber(signUpFormDto.getPhoneNumber())
                .birthDate(signUpFormDto.getBirthDate())
                .point(0) // point_rate 테이블의 가입적립포인트를 가져오는 코드로 수정 필요
                .status(MemberStatus.ACTIVE)
                .rank(rank)
                .build();
        log.info("member build 완료");

        Long userId = memberRepository.save(member).getId();

        // TODO #1 여기 address, location 관련 로직 추가!!
        // TODO #3 TESTCODE 추가!

        return userId;
    }

    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberInfo(Long id) {
        return memberRepository.findMemberInfo(id);
    }
}
