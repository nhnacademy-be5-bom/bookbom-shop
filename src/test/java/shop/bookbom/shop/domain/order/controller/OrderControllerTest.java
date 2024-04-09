package shop.bookbom.shop.domain.order.controller;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
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
import shop.bookbom.shop.domain.order.dto.request.BeforeOrderRequest;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectBookRequest;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectRequest;
import shop.bookbom.shop.domain.order.dto.response.BeforeOrderBookResponse;
import shop.bookbom.shop.domain.order.dto.response.BeforeOrderResponse;
import shop.bookbom.shop.domain.order.dto.response.WrapperSelectResponse;
import shop.bookbom.shop.domain.order.service.OrderService;
import shop.bookbom.shop.domain.wrapper.dto.response.WrapperResponse;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    OrderService orderService;

    @Test
    @DisplayName("주문 전 책 정보 불러오기")
    void showSelectWrapper() throws Exception {
        //given
        List<BeforeOrderRequest> request = new ArrayList<>();
        request.add(new BeforeOrderRequest(1L, 5));

        List<BeforeOrderBookResponse> beforeOrderBookResponseList = new ArrayList<>();
        beforeOrderBookResponseList.add(new BeforeOrderBookResponse("http://img.jpg", "testBook", 5, 15000));

        List<WrapperResponse> wrapperList = new ArrayList<>();
        wrapperList.add(WrapperResponse.builder().id(1L).name("포장지 1").cost(1000).build());

        BeforeOrderResponse response = new BeforeOrderResponse(5, beforeOrderBookResponseList, wrapperList);
        when(orderService.getOrderBookInfo(request)).thenReturn(response);
        //when
        ResultActions perform = mockMvc.perform(post("/shop/orders/before-order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        //then
        perform.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true))
                .andExpect(jsonPath("$.result.totalOrderCount").value(5))
                .andExpect(jsonPath("$.result.beforeOrderBookResponseList.length()").value(1))
                .andExpect(jsonPath("$.result.beforeOrderBookResponseList[0].imageUrl").value("http://img.jpg"))
                .andExpect(jsonPath("$.result.beforeOrderBookResponseList[0].title").value("testBook"))
                .andExpect(jsonPath("$.result.beforeOrderBookResponseList[0].quantity").value(5))
                .andExpect(jsonPath("$.result.beforeOrderBookResponseList[0].cost").value(15000))
                .andExpect(jsonPath("$.result.wrapperList[0].name").value("포장지 1"))
                .andExpect(jsonPath("$.result.wrapperList[0].cost").value(1000));


    }

    @Test
    @DisplayName("포장지 선택 - 회원")
    void selectWrapper_member() throws Exception {
        //given
        List<WrapperSelectBookRequest> wrapperSelectRequestList = new ArrayList<>();
        wrapperSelectRequestList.add(new WrapperSelectBookRequest("test book", "http://img.jpg", "포장지 3", 3, 5000));

        WrapperSelectRequest request = new WrapperSelectRequest(wrapperSelectRequestList, 3);
        Long userId = 20L;
        WrapperSelectResponse response = new WrapperSelectResponse(3, userId, wrapperSelectRequestList);
        when(orderService.selectWrapper(userId, request)).thenReturn(response);
        //when
        ResultActions perform = mockMvc.perform(post("/shop/orders/wrapper")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .param("userId", userId.toString()));
        //then
        perform.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true))
                .andExpect(jsonPath("$.result.totalOrderCount").value(3))
                .andExpect(jsonPath("$.result.userId").value(20L))
                .andExpect(jsonPath("$.result.wrapperSelectRequestList.length()").value(1))
                .andExpect(jsonPath("$.result.wrapperSelectRequestList[0].bookTitle").value("test book"))
                .andExpect(jsonPath("$.result.wrapperSelectRequestList[0].wrapperName").value("포장지 3"));


    }

    @Test
    @DisplayName("포장지 선택- 비회원")
    void selectWrapper_not_member() throws Exception {
        //given
        List<WrapperSelectBookRequest> wrapperSelectRequestList = new ArrayList<>();
        wrapperSelectRequestList.add(new WrapperSelectBookRequest("test book1", "http://img1.jpg", "포장지 3", 3, 5000));
        wrapperSelectRequestList.add(new WrapperSelectBookRequest("test book2", "http://img2.jpg", "포장지 3", 4, 6000));

        WrapperSelectRequest request = new WrapperSelectRequest(wrapperSelectRequestList, 7);
        Long userId = null;
        WrapperSelectResponse response = new WrapperSelectResponse(7, userId, wrapperSelectRequestList);
        when(orderService.selectWrapper(userId, request)).thenReturn(response);
        //when
        ResultActions perform = mockMvc.perform(post("/shop/orders/wrapper")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        //then
        perform.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true))
                .andExpect(jsonPath("$.result.totalOrderCount").value(7))
                .andExpect(jsonPath("$.result.userId").doesNotExist())
                .andExpect(jsonPath("$.result.wrapperSelectRequestList.length()").value(2))
                .andExpect(jsonPath("$.result.wrapperSelectRequestList[0].bookTitle").value("test book1"))
                .andExpect(jsonPath("$.result.wrapperSelectRequestList[0].wrapperName").value("포장지 3"))
                .andExpect(jsonPath("$.result.wrapperSelectRequestList[1].wrapperName").value("포장지 3"))
                .andExpect(jsonPath("$.result.wrapperSelectRequestList[1].quantity").value(4))
                .andExpect(jsonPath("$.result.wrapperSelectRequestList[1].cost").value(6000));


    }
}
