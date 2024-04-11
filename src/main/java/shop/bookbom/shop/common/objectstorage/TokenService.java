package shop.bookbom.shop.common.objectstorage;

import static shop.bookbom.shop.common.objectstorage.dto.TokenRequest.Auth;
import static shop.bookbom.shop.common.objectstorage.dto.TokenRequest.PasswordCredentials;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shop.bookbom.shop.common.objectstorage.dto.ObjectToken;
import shop.bookbom.shop.common.objectstorage.dto.TokenInfo;
import shop.bookbom.shop.common.objectstorage.dto.TokenRequest;

@Service
public class TokenService {
    private final RestTemplate restTemplate;
    private TokenRequest tokenRequest;
    @Value("${object.storage.auth.url}")
    private String authUrl;
    @Value("${object.storage.tenant.id}")
    private String tenantId;
    @Value("${object.storage.username}")
    private String username;
    @Value("${object.storage.password}")
    private String password;

    @Autowired
    public TokenService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    /**
     * Object Storage 파일 업로드를 위한 토큰을 저장하는 메서드입니다.
     * @return 생성된 Token 정보
     */
    public TokenInfo requestToken() {
        if (tokenRequest == null) {
            tokenRequest = new TokenRequest(new Auth(tenantId, new PasswordCredentials(username, password)));
        }
        String identityUrl = this.authUrl + "/tokens";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<TokenRequest> httpEntity = new HttpEntity<>(this.tokenRequest, headers);
        ResponseEntity<ObjectToken> response =
                restTemplate.exchange(identityUrl, HttpMethod.POST, httpEntity, ObjectToken.class);
        return Objects.requireNonNull(response.getBody()).getAccess().getToken();
    }
}
