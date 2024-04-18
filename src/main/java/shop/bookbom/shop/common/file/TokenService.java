package shop.bookbom.shop.common.file;

import static shop.bookbom.shop.common.file.dto.TokenRequest.Auth;
import static shop.bookbom.shop.common.file.dto.TokenRequest.PasswordCredentials;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shop.bookbom.shop.common.file.dto.ObjectToken;
import shop.bookbom.shop.common.file.dto.TokenInfo;
import shop.bookbom.shop.common.file.dto.TokenRequest;
import shop.bookbom.shop.config.SecureManager;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final RestTemplate restTemplate;
    private final SecureManager secureManager;
    private TokenRequest tokenRequest;
    @Value("${object.storage.auth.url}")
    private String authUrl;
    @Value("${object.storage.tenant.id}")
    private String tenantId;
    @Value("${object.storage.username}")
    private String username;
    @Value("${object.storage.password}")
    private String password;


    /**
     * Object Storage 파일 업로드를 위한 토큰을 저장하는 메서드입니다.
     *
     * @return 생성된 Token 정보
     */
    public TokenInfo requestToken() {
        if (tokenRequest == null) {
            PasswordCredentials passwordCredentials = new PasswordCredentials(
                    secureManager.getValue(username), secureManager.getValue(password));
            tokenRequest = new TokenRequest(new Auth(tenantId, passwordCredentials));
        }
        String identityUrl = authUrl + "/tokens";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<TokenRequest> httpEntity = new HttpEntity<>(tokenRequest, headers);
        ResponseEntity<ObjectToken> response =
                restTemplate.exchange(identityUrl, HttpMethod.POST, httpEntity, ObjectToken.class);
        return Objects.requireNonNull(response.getBody()).getAccess().getToken();
    }
}
