package shop.bookbom.shop.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {
    private static final String MYSQL_URL_PREFIX = "jdbc:mysql://";
    private static final String MYSQL_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private final SecureManager secureManager;


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
                .url(MYSQL_URL_PREFIX +
                        secureManager.getValue(ip) +
                        ":" +
                        secureManager.getValue(port) +
                        secureManager.getValue(schema))
                .username(secureManager.getValue(username))
                .password(secureManager.getValue(password))
                .driverClassName(MYSQL_DRIVER_NAME)
                .build();
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }
}
