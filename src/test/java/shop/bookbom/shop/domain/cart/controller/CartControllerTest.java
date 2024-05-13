package shop.bookbom.shop.domain.cart.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static shop.bookbom.shop.common.TestUtils.getCartAddRequest;
import static shop.bookbom.shop.common.TestUtils.getCartInfoResponse;
import static shop.bookbom.shop.common.TestUtils.getCartUpdateRequest;
import static shop.bookbom.shop.common.TestUtils.getCartUpdateResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shop.bookbom.shop.argumentresolver.LoginArgumentResolver;
import shop.bookbom.shop.config.WebConfig;
import shop.bookbom.shop.domain.cart.dto.repsonse.CartInfoResponse;
import shop.bookbom.shop.domain.cart.dto.repsonse.CartUpdateResponse;
import shop.bookbom.shop.domain.cart.dto.request.CartAddRequest;
import shop.bookbom.shop.domain.cart.dto.request.CartUpdateRequest;
import shop.bookbom.shop.domain.cart.service.CartService;
import shop.bookbom.shop.domain.users.dto.UserDto;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(
        value = CartController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebConfig.class))
class CartControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LoginArgumentResolver resolver;

    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    CartService cartService;


    @Test
    @DisplayName("장바구니 상품 추가")
    void addToCart() throws Exception {
        List<CartAddRequest> request = getCartAddRequest();
        ResultActions perform = mockMvc.perform(post("/shop/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        when(resolver.resolveArgument(any(), any(), any(), any())).thenReturn(new UserDto(1L));
        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true));
    }

    @Test
    @DisplayName("장바구니 상품 추가 예외")
    void addToCartException() throws Exception {
        CartAddRequest cartAddRequest = new CartAddRequest(1L, -1);
        ResultActions perform = mockMvc.perform(post("/shop/carts", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of(cartAddRequest))));

        when(resolver.resolveArgument(any(), any(), any(), any())).thenReturn(new UserDto(1L));
        perform
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("요청한 상품 ID와 수량이 올바르지 않습니다."))
                .andExpect(jsonPath("$.header.successful").value(false));
    }

    @Test
    @DisplayName("장바구니 상품 조회")
    void getCart() throws Exception {
        CartInfoResponse response = getCartInfoResponse();
        when(cartService.getCartInfo(any())).thenReturn(response);
        ResultActions perform = mockMvc.perform(get("/shop/carts", 1L));

        when(resolver.resolveArgument(any(), any(), any(), any())).thenReturn(new UserDto(1L));
        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true))
                .andExpect(jsonPath("$.result.cartId").value(1))
                .andExpect(jsonPath("$.result.cartItems.length()").value(3))
                .andExpect(jsonPath("$.result.cartItems[0].bookId").value(1))
                .andExpect(jsonPath("$.result.cartItems[0].quantity").value(1));
    }

    @Test
    @DisplayName("장바구니 상품 수량 변경")
    void updateQuantity() throws Exception {
        CartUpdateRequest request = getCartUpdateRequest();
        CartUpdateResponse response = getCartUpdateResponse();
        when(cartService.updateQuantity(1L, 5)).thenReturn(response);
        ResultActions perform = mockMvc.perform(put("/shop/carts/items/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true))
                .andExpect(jsonPath("$.result.quantity").value(10));
    }

    @Test
    @DisplayName("장바구니 상품 삭제")
    void deleteItem() throws Exception {
        ResultActions perform = mockMvc.perform(delete("/shop/carts/items/{id}", 1L));
        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true));
    }
}
