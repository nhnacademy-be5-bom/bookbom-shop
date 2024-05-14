package shop.bookbom.shop.domain.pointhistory.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static shop.bookbom.shop.common.TestUtils.getPointHistoryResponse;

import java.util.List;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shop.bookbom.shop.argumentresolver.LoginArgumentResolver;
import shop.bookbom.shop.config.WebConfig;
import shop.bookbom.shop.domain.pointhistory.dto.response.PointHistoryResponse;
import shop.bookbom.shop.domain.pointhistory.entity.ChangeReason;
import shop.bookbom.shop.domain.pointhistory.service.PointHistoryService;
import shop.bookbom.shop.domain.users.dto.UserDto;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(
        value = PointHistoryController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {WebConfig.class, LoginArgumentResolver.class}))
class PointHistoryControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    LoginArgumentResolver resolver;
    @MockBean
    PointHistoryService pointHistoryService;

    @Test
    @DisplayName("포인트 내역 조회")
    void getPointHistory() throws Exception {
        PointHistoryResponse pointHistoryResponse = getPointHistoryResponse(ChangeReason.EARN);
        PageRequest pageRequest = PageRequest.of(0, 5);
        PageImpl<PointHistoryResponse> page = new PageImpl<>(List.of(pointHistoryResponse), pageRequest, 1);
        when(pointHistoryService.findPointHistory(any(), any(), any())).thenReturn(page);
        when(resolver.resolveArgument(any(), any(), any(), any())).thenReturn(new UserDto(1L));
        ResultActions perform = mockMvc.perform(get("/shop/users/point-history"));
        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true))
                .andExpect(jsonPath("$.result.content.size()").value(1))
                .andExpect(jsonPath("$.result.content[0].reason").value(ChangeReason.EARN.name()))
                .andExpect(jsonPath("$.result.content[0].changePoint").value(1000));
    }

    @Test
    @DisplayName("필터링 조건 포함한 포인트 내역 조회")
    void getPointHistoryContainsReason() throws Exception {
        PointHistoryResponse pointHistoryResponse = getPointHistoryResponse(ChangeReason.EARN);
        PageRequest pageRequest = PageRequest.of(0, 5);
        PageImpl<PointHistoryResponse> page = new PageImpl<>(List.of(pointHistoryResponse), pageRequest, 1);
        when(pointHistoryService.findPointHistory(any(), any(), eq(ChangeReason.EARN))).thenReturn(page);
        when(pointHistoryService.findPointHistory(any(), any(), eq(ChangeReason.USE))).thenReturn(Page.empty());
        when(resolver.resolveArgument(any(), any(), any(), any())).thenReturn(new UserDto(1L));
        ResultActions perform = mockMvc.perform(get("/shop/users/point-history")
                .param("reason", "USE"));
        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true))
                .andExpect(jsonPath("$.result.content.size()").value(0));
    }

    @Test
    @DisplayName("올바르지 않은 포인트 내역 조회")
    void pointHistoryInvalidReason() throws Exception {
        PointHistoryResponse pointHistoryResponse = getPointHistoryResponse(ChangeReason.EARN);
        PageRequest pageRequest = PageRequest.of(0, 5);
        PageImpl<PointHistoryResponse> page = new PageImpl<>(List.of(pointHistoryResponse), pageRequest, 1);
        when(pointHistoryService.findPointHistory(any(), any(), any())).thenReturn(page);
        when(resolver.resolveArgument(any(), any(), any(), any())).thenReturn(new UserDto(1L));
        ResultActions perform = mockMvc.perform(get("/shop/users/point-history")
                .param("reason", "TESTTT"));
        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("요청하신 포인트 변동사유가 올바르지 않습니다."))
                .andExpect(jsonPath("$.header.successful").value(false));
    }
}
