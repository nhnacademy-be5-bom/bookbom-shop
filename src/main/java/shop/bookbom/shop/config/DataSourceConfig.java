package shop.bookbom.shop.config;

import java.util.Objects;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import shop.bookbom.shop.config.dto.KeystoreResponse;

@Configuration
@Import({SecureManagerConfig.class})
@RequiredArgsConstructor
public class DataSourceConfig {
    private static final String MYSQL_URL_PREFIX = "jdbc:mysql://";
    private static final String MYSQL_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    private final RestTemplate restTemplate;

    @Value("${keymanager.url}")
    private String keyManagerUrl;

    @Value("${secure.manager.appkey}")
    private String appkey;


    @Value("${secure.manager.ip}")
    private String ip;

    @Value("${secure.manager.password}")
    private String password;

    @Value("${secure.manager.port}")
    private String port;

    @Value("${secure.manager.schema}")
    private String schema;

    @Value("${secure.manager.username}")
    private String username;


    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url(MYSQL_URL_PREFIX + getValue(ip) + ":" + getValue(port) + getValue(schema))
                .username(getValue(username))
                .password(getValue(password))
                .driverClassName(MYSQL_DRIVER_NAME)
                .build();
    }

    private String getValue(String key) {
        String url = keyManagerUrl + appkey + "/secrets/" + key;
        KeystoreResponse response = restTemplate.getForObject(url, KeystoreResponse.class);
        return Objects.requireNonNull(response).getBody().getSecret();
    }
}
