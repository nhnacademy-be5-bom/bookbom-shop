package shop.bookbom.shop.common.objectstorage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import shop.bookbom.shop.common.objectstorage.dto.ObjectToken;
import shop.bookbom.shop.common.objectstorage.dto.TokenInfo;
import shop.bookbom.shop.config.SecureManager;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    TokenService tokenService;

    @Mock
    RestTemplate restTemplate;

    @Mock
    SecureManager secureManager;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(tokenService, "authUrl", "http://test.com/test");
        ReflectionTestUtils.setField(tokenService, "tenantId", "tenantId");
        ReflectionTestUtils.setField(tokenService, "username", "testUser");
        ReflectionTestUtils.setField(tokenService, "password", "password");
    }

    @Test
    @DisplayName("Object Storage 토큰 발급 요청")
    void requestToken() {
        //given
        String username = "testUser";
        String password = "password";

        when(secureManager.getValue(username)).thenReturn(username);
        when(secureManager.getValue(password)).thenReturn(password);

        LocalDateTime expires = LocalDateTime.now().plusDays(3);
        LocalDateTime issuedAt = LocalDateTime.now();
        String testId = "testId";
        TokenInfo expectedToken = new TokenInfo(testId, expires, issuedAt);
        ObjectToken.Access access = new ObjectToken.Access(expectedToken);
        ObjectToken objectToken = new ObjectToken(access);
        when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity<?>) any(), (Class<Object>) any()))
                .thenReturn(ResponseEntity.ok(objectToken));
        // when
        TokenInfo response = tokenService.requestToken();

        //then
        assertThat(response.getId()).isEqualTo(testId);
        assertThat(response.getExpires()).isEqualTo(expires);
        assertThat(response.getIssuedAt()).isEqualTo(issuedAt);
    }
}
