package shop.bookbom.shop.argumentresolver;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import shop.bookbom.shop.annotation.Login;
import shop.bookbom.shop.config.JwtSecretKeyProperties;
import shop.bookbom.shop.domain.users.dto.UserDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {
    private final JwtSecretKeyProperties jwtSecretKeyProperties;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginAnnotation =
                parameter.hasParameterAnnotation(Login.class);
        boolean hasUserType =
                UserDto.class.isAssignableFrom(parameter.getParameterType());
        return hasLoginAnnotation && hasUserType;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String authorization = request.getHeader("Authorization");
        if (authorization == null || authorization.isBlank() || !authorization.contains("Bearer ")) {
            return null;
        }
        String token = authorization.replace("Bearer ", "");
        String secretKey = jwtSecretKeyProperties.getSecretKey();
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
        if (claims == null || claims.isEmpty()) {
            return null;
        }
        Long userId = claims.get("userId", Long.class);
        return new UserDto(userId);
    }
}
