package shop.bookbom.shop.domain.member.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.bookbom.shop.domain.member.dto.request.MemberRequestDto;
import shop.bookbom.shop.domain.member.service.MemberService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shop.bookbom.shop.domain.member.service.MemberService;
import shop.bookbom.shop.domain.rank.entity.Rank;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MemberService memberService;

    @Test
    @DisplayName("#1_1 CREATE MEMBER : save test")
    void memberSaveTest() throws Exception {
        MemberRequestDto memberRequestDto =
                MemberRequestDto.builder()
                        .id(1L)
                        .name("hi")
                        .phoneNumber("010-1010-1010")
                        .birthDate(LocalDate.now())
                        .nickname("hi nickname")
                        .build();

        mockMvc.perform(post("/shop/members").content(objectMapper.writeValueAsString(memberRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.header.resultCode").value(200))
                .andExpect(jsonPath("$.header.successful").value(true));
    }

    @Test
    @DisplayName("#1_2 CREATE MEMBER : invalid memberRequestDto test")
    void invalidMemberSaveTest() throws Exception {
        // id = null
        MemberRequestDto memberRequestDto =
                MemberRequestDto.builder()
                        .id(null)
                        .name("hi")
                        .phoneNumber("010-1010-1010")
                        .birthDate(LocalDate.now())
                        .nickname("hi nickname")
                        .build();

        mockMvc.perform(post("/shop/members").content(objectMapper.writeValueAsString(memberRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.header.resultCode").value(400))
                .andExpect(jsonPath("$.header.successful").value(false));


        // name = blank
        memberRequestDto =
                MemberRequestDto.builder()
                        .id(1L)
                        .name(" ")
                        .phoneNumber("010-1010-1010")
                        .birthDate(LocalDate.now())
                        .nickname("hi nickname")
                        .build();

        mockMvc.perform(post("/shop/members").content(objectMapper.writeValueAsString(memberRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.header.resultCode").value(400))
                .andExpect(jsonPath("$.header.successful").value(false));


        // phone number = blank
        memberRequestDto =
                MemberRequestDto.builder()
                        .id(1L)
                        .name("hi")
                        .phoneNumber(" ")
                        .birthDate(LocalDate.now())
                        .nickname("hi nickname")
                        .build();

        mockMvc.perform(post("/shop/members").content(objectMapper.writeValueAsString(memberRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.header.resultCode").value(400))
                .andExpect(jsonPath("$.header.successful").value(false));

        // birthday = null
        memberRequestDto =
                MemberRequestDto.builder()
                        .id(1L)
                        .name("hi")
                        .phoneNumber("010-1010-1010")
                        .birthDate(null)
                        .nickname("hi nickname")
                        .build();

        mockMvc.perform(post("/shop/members").content(objectMapper.writeValueAsString(memberRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.header.resultCode").value(400))
                .andExpect(jsonPath("$.header.successful").value(false));

        // nickname = blank
        memberRequestDto =
                MemberRequestDto.builder()
                        .id(1L)
                        .name("hi")
                        .phoneNumber("010-1010-1010")
                        .birthDate(LocalDate.now())
                        .nickname(" ")
                        .build();

        mockMvc.perform(post("/shop/members").content(objectMapper.writeValueAsString(memberRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.header.resultCode").value(400))
                .andExpect(jsonPath("$.header.successful").value(false));
    }

    @DisplayName("마이 페이지")
    void myPage() throws Exception {
        //given
        Rank rank = getRank(getPointRate());
        when(memberService.getMemberInfo(1L)).thenReturn(
                getMemberInfoResponse(rank, getMember("test@email.com", getRole(), rank)));
        //when
        ResultActions perform = mockMvc.perform(get("/shop/member/my-page")
                .param("userId", "1"));

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
