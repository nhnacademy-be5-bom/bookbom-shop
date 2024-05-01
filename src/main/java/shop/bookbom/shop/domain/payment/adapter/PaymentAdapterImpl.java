package shop.bookbom.shop.domain.payment.adapter;

import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.bookbom.shop.common.exception.ErrorCode;
import shop.bookbom.shop.domain.payment.config.TossPayConfig;
import shop.bookbom.shop.domain.payment.dto.FailureDto;
import shop.bookbom.shop.domain.payment.dto.PaymentRequest;
import shop.bookbom.shop.domain.payment.dto.PaymentResponse;
import shop.bookbom.shop.domain.payment.exception.PaymentFailException;

@Component
@RequiredArgsConstructor
public class PaymentAdapterImpl implements PaymentAdapter {
    private final TossPayConfig tossPayConfig;

    public PaymentResponse requestPaymentConfirm(PaymentRequest paymentRequest) {
        String authorization = Base64.getEncoder().encodeToString(tossPayConfig.getSecretKey().getBytes());
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + authorization);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentRequest> requestEntity = new HttpEntity<>(paymentRequest, headers);

        PaymentResponse response = restTemplate.exchange(tossPayConfig.getConfirmUrl(),
                HttpMethod.POST,
                requestEntity,
                PaymentResponse.class).getBody();

        if (response == null) {
            throw new PaymentFailException(ErrorCode.PAYMENT_FAILED, "결제 응답이 존재하지 않습니다.");
        } else {
            FailureDto failure = response.getFailure();
            if (failure != null) {
                throw new PaymentFailException(ErrorCode.PAYMENT_FAILED, failure.getMessage());
            }
        }
        return response;
    }
}