package shop.bookbom.shop.common.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenRequest {
    private Auth auth;

    public TokenRequest(Auth auth) {
        this.auth = auth;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Auth {
        private String tenantId;
        private PasswordCredentials passwordCredentials;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PasswordCredentials {
        private String username;
        private String password;
    }
}
