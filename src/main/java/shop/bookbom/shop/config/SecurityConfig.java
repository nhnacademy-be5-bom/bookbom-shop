package shop.bookbom.shop.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shop.bookbom.shop.security.filter.JwtAuthenticationFilter;
import shop.bookbom.shop.security.jwt.JwtConfig;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtConfig());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeHttpRequests(
                        (authz) -> authz
                                .and()
                                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                );

        http
                .authorizeHttpRequests(
                        (authz) -> authz
                                .antMatchers("/shop/admin/**").hasAnyRole("ADMIN")
                                .antMatchers("/shop/open/**").permitAll()
                                .antMatchers("/shop/**").hasAnyRole("USER", "MEMBER", "ADMIN")
                                .anyRequest().permitAll()
                );

        return http.build();
    }
}
