package shop.bookbom.shop.domain.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import shop.bookbom.shop.domain.users.controller.UserController;
import shop.bookbom.shop.domain.users.dto.request.ChangeRegisteredRequestDto;
import shop.bookbom.shop.domain.users.dto.request.ResetPasswordRequestDto;
import shop.bookbom.shop.domain.users.dto.request.UserRequestDto;
import shop.bookbom.shop.domain.users.exception.RoleNotFoundException;
import shop.bookbom.shop.domain.users.exception.UserAlreadyExistException;
import shop.bookbom.shop.domain.users.exception.UserNotFoundException;
import shop.bookbom.shop.domain.users.service.UserService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

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

        mockMvc.perform(post("/shop/user").content(objectMapper.writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(1));
    }

    @Test
    @DisplayName("#1_2 CREATE USER : save wrong role test")
    void wrongRoleUserSaveTest() throws Exception {
        UserRequestDto userRequestDto =
                UserRequestDto.builder().email("wow@email.com").password("1").roleName("INVALID_ROLE").build();
        doThrow(new RoleNotFoundException()).when(userService).save(any(UserRequestDto.class));

        mockMvc.perform(post("/shop/user").content(objectMapper.writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.header.resultCode").value(400))
                .andExpect(jsonPath("$.header.successful").value(false));
    }

    @Test
    @DisplayName("#1_2 CREATE USER : save already exist user test")
    void alreadyExistUserSaveTest() throws Exception {
        UserRequestDto userRequestDto =
                UserRequestDto.builder().email("wow@email.com").password("1").roleName("INVALID_ROLE").build();
        doThrow(new UserAlreadyExistException()).when(userService).save(any(UserRequestDto.class));

        mockMvc.perform(post("/shop/user").content(objectMapper.writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.header.resultCode").value(400))
                .andExpect(jsonPath("$.header.successful").value(false));
    }

    @Test
    @DisplayName("#2-1_1 UPDATE USER : reset password")
    void resetPasswordTest() throws Exception {
        ResetPasswordRequestDto resetPasswordRequestDto =
                ResetPasswordRequestDto.builder().id(1L).password("123").build();

        when(userService.save(any(UserRequestDto.class))).thenReturn(1L);

        mockMvc.perform(patch("/shop/user/1/password").content(objectMapper.writeValueAsString(resetPasswordRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.header.resultCode").value(200))
                .andExpect(jsonPath("$.header.successful").value(true));
    }

    @Test
    @DisplayName("#2-1_2 UPDATE USER : reset password with not exist user")
    void resetPasswordWithNoUserTest() throws Exception {
        ResetPasswordRequestDto resetPasswordRequestDto =
                ResetPasswordRequestDto.builder().id(1L).password("123").build();
        doThrow(new UserNotFoundException()).when(userService).resetPassword(any(ResetPasswordRequestDto.class));

        mockMvc.perform(patch("/shop/user/1/password").content(objectMapper.writeValueAsString(resetPasswordRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.header.resultCode").value(400))
                .andExpect(jsonPath("$.header.successful").value(false));
    }

    @Test
    @DisplayName("#2-2_1 UPDATE USER : set registered")
    void changeRegisteredTest() throws Exception {
        ChangeRegisteredRequestDto changeRegisteredRequestDto =
                ChangeRegisteredRequestDto.builder().id(1L).registered(true).build();

        mockMvc.perform(
                        patch("/shop/user/1/registered").content(objectMapper.writeValueAsString(changeRegisteredRequestDto))
                                .contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.header.resultCode").value(200))
                .andExpect(jsonPath("$.header.successful").value(true));
    }

    @Test
    @DisplayName("#2-2_2 UPDATE USER : set registered with not exist user")
    void changeRegisteredWithNoUserTest() throws Exception {
        ChangeRegisteredRequestDto changeRegisteredRequestDto =
                ChangeRegisteredRequestDto.builder().id(-4L).registered(true).build();

        doThrow(new UserNotFoundException()).when(userService).changeRegistered(any(ChangeRegisteredRequestDto.class));

        mockMvc.perform(
                        patch("/shop/user/-4/registered").content(objectMapper.writeValueAsString(changeRegisteredRequestDto))
                                .contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.header.resultCode").value(400))
                .andExpect(jsonPath("$.header.successful").value(false));
    }

    @Test
    @DisplayName("#3-1_1 READ USER : get registered")
    void getRegisteredTest() throws Exception {
        when(userService.isRegistered(any(Long.class))).thenReturn(true);

        mockMvc.perform(get("/shop/user/1/registered").param("id", "1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(200))
                .andExpect(jsonPath("$.result").value(Boolean.TRUE));
    }

    @Test
    @DisplayName("#3-1_2 READ USER : get registered with not exist user")
    void getRegisteredWithNoUserTest() throws Exception {
        doThrow(new UserNotFoundException()).when(userService).isRegistered(any(Long.class));

        mockMvc.perform(get("/shop/user/-4/registered").param("id", "-4").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(400))
                .andExpect(jsonPath("$.header.successful").value(false));
    }

    @Test
    @DisplayName("#3-2_1 READ USER : Check email availability")
    void checkEmailCanUseTest() throws Exception {
        when(userService.checkEmailCanUse(any(String.class))).thenReturn(true);

        mockMvc.perform(get("/shop/user/hi@email").param("email", "hi@email").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(200))
                .andExpect(jsonPath("$.result").value(Boolean.TRUE));


        when(userService.checkEmailCanUse(any(String.class))).thenReturn(false);

        mockMvc.perform(get("/shop/user/hi@email").param("email", "hi@email").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(200))
                .andExpect(jsonPath("$.result").value(Boolean.FALSE));
    }
}
