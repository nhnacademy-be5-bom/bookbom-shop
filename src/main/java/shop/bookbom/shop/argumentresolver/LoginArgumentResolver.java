package shop.bookbom.shop.argumentresolver;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import shop.bookbom.shop.annotation.Login;
import shop.bookbom.shop.domain.users.dto.UserDto;
import shop.bookbom.shop.domain.users.dto.response.UserIdRole;
import shop.bookbom.shop.security.jwt.JwtConfig;

@Slf4j
@RequiredArgsConstructor
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {
    private final JwtConfig jwtConfig;

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
        String token = jwtConfig.resolveToken(request);
        UserIdRole userIdRole = jwtConfig.getUserIdRole(token);
        return new UserDto(userIdRole.getUserId());
    }
}
