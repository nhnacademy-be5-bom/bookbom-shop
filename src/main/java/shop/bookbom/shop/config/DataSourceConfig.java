package shop.bookbom.shop.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {
    private static final int POOL_SIZE = 200;
    private static final String MYSQL_URL_PREFIX = "jdbc:mysql://";
    private static final String MYSQL_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private final SecureManager secureManager;
    private final SecureManagerProperties properties;


    @Bean
    public DataSource dataSource() {
        String ip = secureManager.getValue(properties.getIp());
        String port = secureManager.getValue(properties.getPort());
        String schema = secureManager.getValue(properties.getSchema());
        String username = secureManager.getValue(properties.getUsername());
        String password = secureManager.getValue(properties.getPassword());
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(MYSQL_DRIVER_NAME);
        dataSource.setUrl(MYSQL_URL_PREFIX + ip + ":" + port + schema);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setTestOnBorrow(true);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setInitialSize(POOL_SIZE);
        dataSource.setMaxTotal(POOL_SIZE);
        dataSource.setMaxIdle(POOL_SIZE);
        dataSource.setMinIdle(POOL_SIZE);
        dataSource.setMaxWaitMillis(5000);
        return dataSource;
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }
}
