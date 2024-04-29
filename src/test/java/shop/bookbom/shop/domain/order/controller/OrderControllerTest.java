package shop.bookbom.shop.domain.order.controller;


import static org.mockito.ArgumentMatchers.any;
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
import shop.bookbom.shop.common.exception.ErrorCode;
import shop.bookbom.shop.domain.order.dto.request.BeforeOrderRequest;
import shop.bookbom.shop.domain.order.dto.request.BeforeOrderRequestList;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectBookRequest;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectRequest;
import shop.bookbom.shop.domain.order.dto.response.BeforeOrderBookResponse;
import shop.bookbom.shop.domain.order.dto.response.BeforeOrderResponse;
import shop.bookbom.shop.domain.order.dto.response.WrapperSelectBookResponse;
import shop.bookbom.shop.domain.order.dto.response.WrapperSelectResponse;
import shop.bookbom.shop.domain.order.service.OrderService;
import shop.bookbom.shop.domain.wrapper.dto.WrapperDto;

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
        List<BeforeOrderRequest> beforeOrderRequestList = new ArrayList<>();
        beforeOrderRequestList.add(new BeforeOrderRequest(1L, 5));
        BeforeOrderRequestList request = new BeforeOrderRequestList(beforeOrderRequestList);

        List<BeforeOrderBookResponse> beforeOrderBookResponseList = new ArrayList<>();
        beforeOrderBookResponseList.add(new BeforeOrderBookResponse(1L, "http://img.jpg", "testBook", 5, 15000));

        List<WrapperDto> wrapperList = new ArrayList<>();
        wrapperList.add(new WrapperDto(1L, "포장지 1", 1000));

        BeforeOrderResponse response = new BeforeOrderResponse(5, beforeOrderBookResponseList, wrapperList);
        when(orderService.getOrderBookInfo(any())).thenReturn(response);
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
    @DisplayName("주문 전 - 요청 값 유효성 검사 - bookId가 null일 때")
    void beforeorder_invalidRequestException_IdIsNull() throws Exception {
        //given
        List<BeforeOrderRequest> beforeOrderRequestList = new ArrayList<>();
        beforeOrderRequestList.add(new BeforeOrderRequest(null, 5));
        BeforeOrderRequestList request = new BeforeOrderRequestList(beforeOrderRequestList);

        //when, then
        ResultActions perform = mockMvc.perform(post("/shop/orders/before-order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        perform.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(400))
                .andExpect(jsonPath("$.header.resultMessage").value(ErrorCode.COMMON_INVALID_PARAMETER.getMessage()))
                .andExpect(jsonPath("$.header.successful").value(false));

    }

    @Test
    @DisplayName("주문 전 - 요청 값 유효성 검사 - 수량이 0일 때")
    void beforeorder_invalidRequestException_quantityIsZero() throws Exception {
        //given
        List<BeforeOrderRequest> beforeOrderRequestList = new ArrayList<>();
        beforeOrderRequestList.add(new BeforeOrderRequest(20L, 0));
        BeforeOrderRequestList request = new BeforeOrderRequestList(beforeOrderRequestList);

        //when, then
        ResultActions perform = mockMvc.perform(post("/shop/orders/before-order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        perform.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(400))
                .andExpect(jsonPath("$.header.resultMessage").value(ErrorCode.COMMON_INVALID_PARAMETER.getMessage()))
                .andExpect(jsonPath("$.header.successful").value(false));

    }

    @Test
    @DisplayName("포장지 선택 - 회원")
    void selectWrapper_member() throws Exception {
        //given
        List<WrapperSelectBookRequest> wrapperSelectRequestList = new ArrayList<>();
        wrapperSelectRequestList.add(new WrapperSelectBookRequest(1L, "test book", "http://img.jpg", "포장지 3"
                , 3, 5000));

        WrapperSelectRequest request = new WrapperSelectRequest(wrapperSelectRequestList, 3);
        Long userId = 20L;
        List<WrapperSelectBookResponse> wrapperSelectBookResponseList = new ArrayList<>();
        wrapperSelectBookResponseList.add(
                WrapperSelectBookResponse.builder().bookTitle("test book").imgUrl("http://img.jpg").wrapperName("포장지 3")
                        .quantity(3).cost(5000).build());
        List<String> estimatedDateList = new ArrayList<>();
        estimatedDateList.add("4/26(금)");
        estimatedDateList.add("4/29(월)");
        estimatedDateList.add("4/30(화)");
        estimatedDateList.add("5/1(수)");
        estimatedDateList.add("5/2(목)");

        WrapperSelectResponse response = WrapperSelectResponse.builder().totalOrderCount(3)
                .userId(userId)
                .wrapperSelectResponseList(wrapperSelectBookResponseList)
                .estimatedDateList(estimatedDateList).build();
        when(orderService.selectWrapper(any(), any())).thenReturn(response);
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
                .andExpect(jsonPath("$.result.wrapperSelectResponseList.length()").value(1))
                .andExpect(jsonPath("$.result.wrapperSelectResponseList[0].bookTitle").value("test book"))
                .andExpect(jsonPath("$.result.wrapperSelectResponseList[0].wrapperName").value("포장지 3"))
                .andExpect(jsonPath("$.result.estimatedDateList[0]").value("4/26(금)"));


    }

    @Test
    @DisplayName("포장지 선택- 비회원")
    void selectWrapper_not_member() throws Exception {
        //given
        List<WrapperSelectBookRequest> wrapperSelectRequestList = new ArrayList<>();
        wrapperSelectRequestList.add(
                new WrapperSelectBookRequest(1L, "test book1", "http://img1.jpg", "포장지 3", 3, 5000));
        wrapperSelectRequestList.add(
                new WrapperSelectBookRequest(2L, "test book2", "http://img2.jpg", "포장지 3", 4, 6000));

        WrapperSelectRequest request = new WrapperSelectRequest(wrapperSelectRequestList, 7);
        Long userId = null;
        List<WrapperSelectBookResponse> wrapperSelectBookResponseList = new ArrayList<>();
        wrapperSelectBookResponseList.add(
                WrapperSelectBookResponse.builder().bookTitle("test book1").imgUrl("http://img1.jpg")
                        .wrapperName("포장지 3")
                        .quantity(3).cost(5000).build());
        wrapperSelectBookResponseList.add(
                WrapperSelectBookResponse.builder().bookTitle("test book2").imgUrl("http://img2.jpg")
                        .wrapperName("포장지 3")
                        .quantity(4).cost(6000).build());

        List<String> estimatedDateList = new ArrayList<>();
        estimatedDateList.add("4/26(금)");
        estimatedDateList.add("4/29(월)");
        estimatedDateList.add("4/30(화)");
        estimatedDateList.add("5/1(수)");
        estimatedDateList.add("5/2(목)");
        WrapperSelectResponse response = WrapperSelectResponse.builder().totalOrderCount(7)
                .userId(userId)
                .wrapperSelectResponseList(wrapperSelectBookResponseList)
                .estimatedDateList(estimatedDateList).build();
        when(orderService.selectWrapper(any(), any())).thenReturn(response);
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
                .andExpect(jsonPath("$.result.wrapperSelectResponseList.length()").value(2))
                .andExpect(jsonPath("$.result.wrapperSelectResponseList[0].bookTitle").value("test book1"))
                .andExpect(jsonPath("$.result.wrapperSelectResponseList[0].wrapperName").value("포장지 3"))
                .andExpect(jsonPath("$.result.wrapperSelectResponseList[1].wrapperName").value("포장지 3"))
                .andExpect(jsonPath("$.result.wrapperSelectResponseList[1].quantity").value(4))
                .andExpect(jsonPath("$.result.wrapperSelectResponseList[1].cost").value(6000));


    }

    @Test
    @DisplayName("포장지 선택- 요청 정보가 invalid")
    void selectWrapper_invalidRequest() throws Exception {
        //given
        List<WrapperSelectBookRequest> wrapperSelectRequestList = new ArrayList<>();
        wrapperSelectRequestList.add(
                new WrapperSelectBookRequest(1L, " ", "http://img1.jpg", "포장지 3", 3, 5000));
        wrapperSelectRequestList.add(
                new WrapperSelectBookRequest(2L, "test book2", "http://img2.jpg", "포장지 3", 4, 6000));

        WrapperSelectRequest request = new WrapperSelectRequest(wrapperSelectRequestList, 7);

        //when
        ResultActions perform = mockMvc.perform(post("/shop/orders/wrapper")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        //then
        perform.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(400))
                .andExpect(jsonPath("$.header.resultMessage").value(ErrorCode.COMMON_INVALID_PARAMETER.getMessage()))
                .andExpect(jsonPath("$.header.successful").value(false));

    }
}
