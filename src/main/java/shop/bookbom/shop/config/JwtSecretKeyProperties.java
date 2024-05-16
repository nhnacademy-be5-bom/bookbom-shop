package shop.bookbom.shop.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "jwt")
@ConstructorBinding
@RequiredArgsConstructor
public class JwtSecretKeyProperties {
    private final String secretKey;
}
