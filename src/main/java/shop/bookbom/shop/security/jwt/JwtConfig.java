package shop.bookbom.shop.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import shop.bookbom.shop.domain.users.dto.response.UserIdRole;
import shop.bookbom.shop.security.exception.TokenNotValidateException;

@Slf4j
@RequiredArgsConstructor
public class JwtConfig {

    @Value("${jwt.secret-key}")
    private String secretKey;

    public String resolveToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || authorization.isBlank() || !authorization.contains("Bearer ")) {
            return null;
        }
        String basic = authorization.replace("Bearer ", "");
        log.info("authorization : {}", authorization);
        log.info("bearer : {}", basic);
        return basic;
    }

    public UserIdRole getUserIdRole(String accessToken) {
        if (!validateToken(accessToken)) {
            throw new TokenNotValidateException();
        }
        Claims claims = getClaims(accessToken);
        return UserIdRole.builder()
                .userId(claims.get("userId", Long.class))
                .role(claims.get("role", String.class))
                .build();
    }

    // 토큰의 유효성 확인
    public boolean validateToken(String jwtToken) {
        try {
            Claims claims = getClaims(jwtToken);
            return claims.getExpiration().before(new Date()) == false;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaims(String jwtToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
        if (claims == null || claims.isEmpty()) {
            throw new TokenNotValidateException();
        }
        return claims;
    }
}
