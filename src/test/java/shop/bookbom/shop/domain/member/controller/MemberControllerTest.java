package shop.bookbom.shop.domain.member.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static shop.bookbom.shop.common.TestUtils.getMember;
import static shop.bookbom.shop.common.TestUtils.getMemberInfoResponse;
import static shop.bookbom.shop.common.TestUtils.getPointRate;
import static shop.bookbom.shop.common.TestUtils.getRank;
import static shop.bookbom.shop.common.TestUtils.getRole;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shop.bookbom.shop.argumentresolver.LoginArgumentResolver;
import shop.bookbom.shop.config.WebConfig;
import shop.bookbom.shop.domain.member.service.MemberService;
import shop.bookbom.shop.domain.rank.entity.Rank;
import shop.bookbom.shop.domain.users.dto.UserDto;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(
        value = MemberController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebConfig.class))
class MemberControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @MockBean
    LoginArgumentResolver resolver;

    @Test
    @DisplayName("마이 페이지")
    void myPage() throws Exception {
        //given
        Rank rank = getRank(getPointRate());
        when(memberService.getMemberInfo(any())).thenReturn(
                getMemberInfoResponse(rank, getMember("test@email.com", getRole(), rank)));
        when(resolver.resolveArgument(any(), any(), any(), any())).thenReturn(new UserDto(1L));
        //when
        ResultActions perform = mockMvc.perform(get("/shop/users/my-page"));

        //then
        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true))
                .andExpect(jsonPath("$.result.nickname").value("test"))
                .andExpect(jsonPath("$.result.rank").value("test"))
                .andExpect(jsonPath("$.result.point").value(1000))
                .andExpect(jsonPath("$.result.couponCount").value(0))
                .andExpect(jsonPath("$.result.wishCount").value(2));
    }
}
