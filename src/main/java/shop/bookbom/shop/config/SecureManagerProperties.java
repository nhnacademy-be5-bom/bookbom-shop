package shop.bookbom.shop.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "secure.manager")
@ConstructorBinding
@RequiredArgsConstructor
public class SecureManagerProperties {
    private final String ip;
    private final String password;
    private final String port;
    private final String schema;
    private final String username;

}
