package shop.bookbom.shop.domain.member.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
}

