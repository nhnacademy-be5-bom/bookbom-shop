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
import static shop.bookbom.shop.domain.cart.service.CartTestUtils.getCartAddRequest;
import static shop.bookbom.shop.domain.cart.service.CartTestUtils.getCartInfoResponse;
import static shop.bookbom.shop.domain.cart.service.CartTestUtils.getCartUpdateRequest;
import static shop.bookbom.shop.domain.cart.service.CartTestUtils.getCartUpdateResponse;

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
import shop.bookbom.shop.domain.cart.dto.repsonse.CartInfoResponse;
import shop.bookbom.shop.domain.cart.dto.repsonse.CartUpdateResponse;
import shop.bookbom.shop.domain.cart.dto.request.CartAddRequest;
import shop.bookbom.shop.domain.cart.dto.request.CartUpdateRequest;
import shop.bookbom.shop.domain.cart.service.CartService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    CartService cartService;

    @Test
    @DisplayName("장바구니 상품 추가")
    void addToCart() throws Exception {
        List<CartAddRequest> request = getCartAddRequest();
        CartInfoResponse response = getCartInfoResponse();
        when(cartService.addCart(request, 1L)).thenReturn(response);
        ResultActions perform = mockMvc.perform(post("/shop/carts/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true))
                .andExpect(jsonPath("$.result.cart_id").value(1))
                .andExpect(jsonPath("$.result.cart_items.length()").value(3))
                .andExpect(jsonPath("$.result.cart_items[0].bookId").value(1))
                .andExpect(jsonPath("$.result.cart_items[0].quantity").value(1));
    }

    @Test
    @DisplayName("장바구니 상품 조회")
    void getCart() throws Exception {
        CartInfoResponse response = getCartInfoResponse();
        when(cartService.getCartInfo(any())).thenReturn(response);
        ResultActions perform = mockMvc.perform(get("/shop/carts/{id}", 1L));
        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true))
                .andExpect(jsonPath("$.result.cart_id").value(1))
                .andExpect(jsonPath("$.result.cart_items.length()").value(3))
                .andExpect(jsonPath("$.result.cart_items[0].bookId").value(1))
                .andExpect(jsonPath("$.result.cart_items[0].quantity").value(1));
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