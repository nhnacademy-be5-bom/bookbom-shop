package shop.bookbom.shop.domain.payment.dto;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TossPayConfig {

    @Value("${tosspay.client-key}")
    private String clientKey;

    @Value("${tosspay.secret-key}")
    private String secretKey;

    @Value("${tosspay.success-url}")
    private String successUrl;

    @Value("${tosspay.fail-url}")
    private String failUrl;

    public static final String URL = "https://api.tosspayments/com/v1/payments/";
}
