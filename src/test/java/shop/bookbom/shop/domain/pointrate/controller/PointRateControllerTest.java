package shop.bookbom.shop.domain.pointrate.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shop.bookbom.shop.domain.pointrate.dto.request.PointRateUpdateRequest;
import shop.bookbom.shop.domain.pointrate.entity.EarnPointType;
import shop.bookbom.shop.domain.pointrate.repository.dto.PointRateResponse;
import shop.bookbom.shop.domain.pointrate.service.PointRateService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PointRateController.class)
class PointRateControllerTest {
    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    PointRateService pointRateService;

    @Test
    @DisplayName("포인트 적립 정책 목록 조회")
    void getAllPolicies() throws Exception {
        //given
        PointRateResponse pointRate1 = new PointRateResponse(1L, "테스트1", EarnPointType.RATE, 100);
        PointRateResponse pointRate2 = new PointRateResponse(2L, "테스트2", EarnPointType.COST, 1000);
        when(pointRateService.getPointPolicies()).thenReturn(List.of(pointRate1, pointRate2));

        //when
        ResultActions perform = mockMvc.perform(get("/shop/point-rate"));
        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true))
                .andExpect(jsonPath("$.result.length()").value(2))
                .andExpect(jsonPath("$.result[0].id").value(1))
                .andExpect(jsonPath("$.result[0].name").value("테스트1"))
                .andExpect(jsonPath("$.result[0].earnType").value("RATE"))
                .andExpect(jsonPath("$.result[0].earnPoint").value(100));
    }

    @Test
    @DisplayName("포인트 적립 정책 수정")
    void pointRateUpdate() throws Exception {
        //given
        PointRateResponse pointRate = new PointRateResponse(1L, "수정 테스트1", EarnPointType.COST, 100);
        when(pointRateService.updatePolicy(anyLong(), anyString(), anyInt())).thenReturn(pointRate);
        PointRateUpdateRequest request = new PointRateUpdateRequest("COST", 100);
        //when
        ResultActions perform = mockMvc.perform(put("/shop/point-rate/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true))
                .andExpect(jsonPath("$.result.id").value(1))
                .andExpect(jsonPath("$.result.name").value("수정 테스트1"))
                .andExpect(jsonPath("$.result.earnType").value("COST"))
                .andExpect(jsonPath("$.result.earnPoint").value(100));
    }

    @Test
    @DisplayName("포인트 적립 정책 수정 예외")
    void pointRateUpdateException() throws Exception {
        //given
        PointRateUpdateRequest request = new PointRateUpdateRequest("COST", 0);
        //when
        ResultActions perform = mockMvc.perform(put("/shop/point-rate/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        perform
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(400))
                .andExpect(jsonPath("$.header.resultMessage").value("요청한 값이 올바르지 않습니다."))
                .andExpect(jsonPath("$.header.successful").value(false));
    }
}
