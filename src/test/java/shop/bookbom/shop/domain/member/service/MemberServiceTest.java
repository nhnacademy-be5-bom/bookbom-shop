package shop.bookbom.shop.domain.member.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static shop.bookbom.shop.common.TestUtils.getMember;
import static shop.bookbom.shop.common.TestUtils.getMemberInfoResponse;
import static shop.bookbom.shop.common.TestUtils.getOrder;
import static shop.bookbom.shop.common.TestUtils.getOrderStatus;
import static shop.bookbom.shop.common.TestUtils.getPointRate;
import static shop.bookbom.shop.common.TestUtils.getRank;
import static shop.bookbom.shop.common.TestUtils.getRole;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.bookbom.shop.domain.member.dto.response.MemberInfoResponse;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.repository.MemberRepository;
import shop.bookbom.shop.domain.member.service.impl.MemberServiceImpl;
import shop.bookbom.shop.domain.rank.entity.Rank;
import shop.bookbom.shop.domain.member.dto.request.MemberRequestDto;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    @Test
    public void testSave() {
        MemberRequestDto memberRequestDto =
                MemberRequestDto.builder()
                        .id(1L)
                        .name("name")
                        .nickname("nickname")
                        .phoneNumber("010-0101-1010")
                        .birthDate(LocalDate.now()).build();
        memberService.save(memberRequestDto);

        verify(memberRepository).save(any(Member.class));
    }

    @Test
    @DisplayName("마이 페이지 회원 정보 조회")
    void getMemberInfo() {
        //given
        Rank rank = getRank(getPointRate());
        Member member = getMember("test@email.com", getRole(), rank);
        member.addOrder(getOrder(member, getOrderStatus()));
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
}
