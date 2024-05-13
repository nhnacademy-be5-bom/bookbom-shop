package shop.bookbom.shop.security.filter;


import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.bookbom.shop.domain.users.dto.response.UserIdRole;
import shop.bookbom.shop.security.authentication.UserIdPasswordAuthenticationToken;
import shop.bookbom.shop.security.jwt.JwtConfig;

/**
 * jwt 토큰이 존재한다면 spring security의 context에 넣어줌
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("now doing jwtAuthenticationFilter");

        String jwt = jwtConfig.resolveToken(request);
        if (jwt != null) {
            UserIdRole userIdRole = jwtConfig.getUserIdRole(jwt);

            GrantedAuthority a = new SimpleGrantedAuthority(userIdRole.getRole());
            var auth = new UserIdPasswordAuthenticationToken(
                    userIdRole.getUserId(),
                    null,
                    List.of(a)
            );

            SecurityContextHolder.getContext()
                    .setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * /shop/open/** 이하의 경로와
     * /error
     * 에서는 필터가 동작하지 않는다.
     */

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().startsWith("/shop/open") ||
                request.getServletPath().equals("/error");
    }
}
