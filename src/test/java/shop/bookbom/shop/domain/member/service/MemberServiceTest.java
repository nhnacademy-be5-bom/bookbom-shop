package shop.bookbom.shop.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static shop.bookbom.shop.common.TestUtils.getMember;
import static shop.bookbom.shop.common.TestUtils.getMemberInfoResponse;
import static shop.bookbom.shop.common.TestUtils.getOrder;
import static shop.bookbom.shop.common.TestUtils.getOrderStatus;
import static shop.bookbom.shop.common.TestUtils.getPointRate;
import static shop.bookbom.shop.common.TestUtils.getRank;
import static shop.bookbom.shop.common.TestUtils.getRole;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.bookbom.shop.domain.member.dto.request.MemberRequestDto;
import shop.bookbom.shop.domain.member.dto.request.SignUpFormDto;
import shop.bookbom.shop.domain.member.dto.response.MemberInfoResponse;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.repository.MemberRepository;
import shop.bookbom.shop.domain.member.service.impl.MemberServiceImpl;
import shop.bookbom.shop.domain.pointrate.entity.ApplyPointType;
import shop.bookbom.shop.domain.pointrate.entity.EarnPointType;
import shop.bookbom.shop.domain.pointrate.entity.PointRate;
import shop.bookbom.shop.domain.rank.entity.Rank;
import shop.bookbom.shop.domain.role.entity.Role;
import shop.bookbom.shop.domain.role.repository.RoleRepository;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    @Test
    public void saveTest() {
        MemberRequestDto memberRequestDto =
                MemberRequestDto.builder()
                        .id(1L)
                        .name("name")
                        .nickname("nickname")
                        .phoneNumber("010-0101-1010")
                        .birthDate(LocalDate.now())
                        .status("ACTIVE")
                        .roleName("ROLE_USER")
                        .rankName("STANDARD").build();
        memberService.save(memberRequestDto);

        verify(memberRepository).save(any(Member.class));
    }

    @Test
    @DisplayName("마이 페이지 회원 정보 조회")
    void getMemberInfo() {
        //given
        Rank rank = getRank(getPointRate());
        Member member = getMember("test@email.com", getRole(), rank);
        member.addOrder(getOrder(member, getOrderStatus(), LocalDateTime.now()));
        MemberInfoResponse response =
                getMemberInfoResponse(rank, member);
        when(memberRepository.findMemberInfo(any())).thenReturn(response);
        //when
        MemberInfoResponse memberInfo = memberService.getMemberInfo(1L);

        //then
        assertThat(memberInfo).isNotNull();
        assertThat(memberInfo.getNickname()).isEqualTo("test");
        assertThat(memberInfo.getRank()).isEqualTo("test");
        assertThat(memberInfo.getPoint()).isEqualTo(1000);
        assertThat(memberInfo.getCouponCount()).isZero();
        assertThat(memberInfo.getWishCount()).isEqualTo(2);
    }

    public void signUpTest() {
        SignUpFormDto signUpFormDto =
                SignUpFormDto.builder()
                        .email("test@email.com")
                        .password("testpassword")
                        .name("name")
                        .nickname("nickname")
                        .phoneNumber("010-0101-1010")
                        .birthDate(LocalDate.now())
                        .addressNumber("addr")
                        .location("loc").build();


        Role role = Role.builder()
                .id(1L)
                .name("ROLE_USER")
                .build();

        Rank rank = Rank.builder()
                .pointRate(PointRate.builder()
                        .earnType(EarnPointType.RATE)
                        .earnPoint(1)
                        .applyType(ApplyPointType.BOOK)
                        .createdAt(LocalDateTime.now())
                        .name("pointRate").build())
                .name("STANDARD")
                .build();

        when(roleRepository.findByName(any())).thenReturn(Optional.of(role));

        // TODO rank repository mock 설정 필요

        memberService.signUp(signUpFormDto);

        verify(memberRepository).save(any(Member.class));
    }
}
