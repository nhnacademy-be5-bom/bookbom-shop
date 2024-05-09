package shop.bookbom.shop.domain.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import shop.bookbom.shop.domain.users.controller.OpenUserController;
import shop.bookbom.shop.domain.users.service.UserService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(OpenUserController.class)
class OpenUserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @Test
    @DisplayName("이메일 중복 체크")
    void checkEmailCanUseTest() throws Exception {
        when(userService.checkEmailCanUse(any(String.class))).thenReturn(true);

        mockMvc.perform(get("/shop/open/users/check-email")
                        .param("email", "hi@email.com"))
                .andExpect(jsonPath("$.header.resultCode").value(200))
                .andExpect(jsonPath("$.result.canUse").value(true));
    }

    @Test
    @DisplayName("이메일 중복 체크 - 이메일 미입력")
    void checkEmailCanUseTestInvalidRequest() throws Exception {
        mockMvc.perform(get("/shop/open/users/check-email"))
                .andExpect(jsonPath("$.header.resultCode").value(400))
                .andExpect(jsonPath("$.header.resultMessage").value("이메일을 입력해주세요."));
    }
}
