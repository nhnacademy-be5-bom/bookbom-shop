package shop.bookbom.shop.domain.payment.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TossPayConfig {

    @Value("${tosspay.confirm-url}")
    private String confirmUrl;

    @Value("${tosspay.authorization}")
    private String authorization;

    @Value("${tosspay.client-key}")
    private String clientKey;

    @Value("${tosspay.secret-key}")
    private String secretKey;
}
