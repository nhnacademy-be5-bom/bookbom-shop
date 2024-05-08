package shop.bookbom.shop.domain.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static shop.bookbom.shop.common.TestUtils.getOrder;
import static shop.bookbom.shop.common.TestUtils.getOrderInfoResponse;
import static shop.bookbom.shop.common.TestUtils.getOrderStatus;
import static shop.bookbom.shop.common.TestUtils.getRole;
import static shop.bookbom.shop.common.TestUtils.getUser;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shop.bookbom.shop.domain.order.dto.response.OrderInfoResponse;
import shop.bookbom.shop.domain.users.controller.UserController;
import shop.bookbom.shop.domain.users.dto.request.ResetPasswordRequestDto;
import shop.bookbom.shop.domain.users.dto.request.UserRequestDto;
import shop.bookbom.shop.domain.users.entity.User;
import shop.bookbom.shop.domain.users.exception.RoleNotFoundException;
import shop.bookbom.shop.domain.users.exception.UserAlreadyExistException;
import shop.bookbom.shop.domain.users.exception.UserNotFoundException;
import shop.bookbom.shop.domain.users.service.UserService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @Test
    @DisplayName("#1_1 CREATE USER : save test")
    void userSaveTest() throws Exception {
        UserRequestDto userRequestDto =
                UserRequestDto.builder().email("wow@email.com").password("1").roleName("ROLE_USER").build();

        when(userService.save(any(UserRequestDto.class))).thenReturn(1L);

        mockMvc.perform(post("/shop/users").content(objectMapper.writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(1));
    }

    @Test
    @DisplayName("#1_2 CREATE USER : save wrong role test")
    void wrongRoleUserSaveTest() throws Exception {
        UserRequestDto userRequestDto =
                UserRequestDto.builder().email("wow@email.com").password("1").roleName("INVALID_ROLE").build();
        doThrow(new RoleNotFoundException()).when(userService).save(any(UserRequestDto.class));

        mockMvc.perform(post("/shop/users").content(objectMapper.writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.header.resultCode").value(400))
                .andExpect(jsonPath("$.header.successful").value(false));
    }

    @Test
    @DisplayName("#1_2 CREATE USER : save already exist user test")
    void alreadyExistUserSaveTest() throws Exception {
        UserRequestDto userRequestDto =
                UserRequestDto.builder().email("wow@email.com").password("1").roleName("INVALID_ROLE").build();
        doThrow(new UserAlreadyExistException()).when(userService).save(any(UserRequestDto.class));

        mockMvc.perform(post("/shop/users").content(objectMapper.writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.header.resultCode").value(400))
                .andExpect(jsonPath("$.header.successful").value(false));
    }

    @Test
    @DisplayName("#1_2 CREATE USER : save invalid email user test")
    void wrongEmailUserSaveTest() throws Exception {
        UserRequestDto userRequestDto =
                UserRequestDto.builder().email("INVALID_EMAIL").password("1").roleName("ROLE_USER").build();
//        doThrow(new UserAlreadyExistException()).when(userService).save(any(UserRequestDto.class));

        mockMvc.perform(post("/shop/users").content(objectMapper.writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.header.resultCode").value(400))
                .andExpect(jsonPath("$.header.successful").value(false));
    }

    @Test
    @DisplayName("#2-1_1 UPDATE USER : reset password")
    void resetPasswordTest() throws Exception {
        ResetPasswordRequestDto resetPasswordRequestDto =
                ResetPasswordRequestDto.builder().id(1L).password("123").build();

        when(userService.save(any(UserRequestDto.class))).thenReturn(1L);

        mockMvc.perform(
                        patch("/shop/users/1/password").content(objectMapper.writeValueAsString(resetPasswordRequestDto))
                                .contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.header.resultCode").value(200))
                .andExpect(jsonPath("$.header.successful").value(true));
    }

    @Test
    @DisplayName("#2-1_2 UPDATE USER : reset password with not exist user")
    void resetPasswordWithNoUserTest() throws Exception {
        ResetPasswordRequestDto resetPasswordRequestDto =
                ResetPasswordRequestDto.builder().id(1L).password("123").build();
        doThrow(new UserNotFoundException()).when(userService).resetPassword(any(ResetPasswordRequestDto.class));

        mockMvc.perform(
                        patch("/shop/users/1/password").content(objectMapper.writeValueAsString(resetPasswordRequestDto))
                                .contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.header.resultCode").value(400))
                .andExpect(jsonPath("$.header.successful").value(false));
    }

    @Test
    @DisplayName("#2-2_1 UPDATE USER : set registered")
    void changeRegisteredTest() throws Exception {
        mockMvc.perform(
                        patch("/shop/users/1/registered").param("registered", "false")
                                .contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.header.resultCode").value(200))
                .andExpect(jsonPath("$.header.successful").value(true));
    }

    @Test
    @DisplayName("#2-2_2 UPDATE USER : set registered with not exist user")
    void changeRegisteredWithNoUserTest() throws Exception {

        doThrow(new UserNotFoundException()).when(userService)
                .changeRegistered(any(Long.class), any(Boolean.class));

        mockMvc.perform(
                        patch("/shop/users/-4/registered").param("registered", "true")
                                .contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.header.resultCode").value(400))
                .andExpect(jsonPath("$.header.successful").value(false));
    }

    @Test
    @DisplayName("#3-1_1 READ USER : get registered")
    void getRegisteredTest() throws Exception {
        when(userService.isRegistered(any(Long.class))).thenReturn(true);

        mockMvc.perform(get("/shop/users/1/registered").param("id", "1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(200))
                .andExpect(jsonPath("$.result").value(Boolean.TRUE));
    }

    @Test
    @DisplayName("#3-1_2 READ USER : get registered with not exist user")
    void getRegisteredWithNoUserTest() throws Exception {
        doThrow(new UserNotFoundException()).when(userService).isRegistered(any(Long.class));

        mockMvc.perform(get("/shop/users/-4/registered").param("id", "-4").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(400))
                .andExpect(jsonPath("$.header.successful").value(false));
    }

    @Test
    @DisplayName("주문 내역 조회")
    void getOrders() throws Exception {
        //given
        User user = getUser("test@email.com", "password", getRole());
        List<OrderInfoResponse> content = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            content.add(getOrderInfoResponse(getOrder(user, getOrderStatus(), LocalDateTime.now().minusDays(i))));
        }
        PageRequest pageRequest = PageRequest.of(0, 10);
        when(userService.getOrderInfos(any(), any(), any()))
                .thenReturn(new PageImpl<>(content, pageRequest, content.size()));
        //when
        ResultActions perform = mockMvc.perform(get("/shop/users/orders")
                .param("userId", "1"));
        //then
        perform
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.successful").value(true))
                .andExpect(jsonPath("$.result.content.size()").value(content.size()));
    }

    @Test
    @DisplayName("주문 내역 조회 - 날짜 조건 추가")
    void getOrdersFilteredDate() throws Exception {
        //given
        User user = getUser("test@email.com", "password", getRole());
        List<OrderInfoResponse> content = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            content.add(getOrderInfoResponse(getOrder(user, getOrderStatus(), LocalDateTime.now().minusDays(i))));
        }
        PageRequest pageRequest = PageRequest.of(0, 10);
        when(userService.getOrderInfos(any(), any(), any()))
                .thenReturn(new PageImpl<>(content, pageRequest, content.size()));
        //when
        ResultActions perform = mockMvc.perform(get("/shop/users/orders")
                .param("userId", "1")
                .param("date_from", LocalDate.now().minusDays(7).toString())
                .param("date_to", LocalDate.now().minusDays(1).toString()));
        //then
        perform
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.successful").value(true))
                .andExpect(jsonPath("$.result.content.size()").value(content.size()));
    }
}
