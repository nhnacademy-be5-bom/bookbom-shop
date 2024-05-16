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
import shop.bookbom.shop.domain.payment.dto.request.PaymentCancelRequest;
import shop.bookbom.shop.domain.payment.dto.request.PaymentRequest;
import shop.bookbom.shop.domain.payment.dto.response.FailureDto;
import shop.bookbom.shop.domain.payment.dto.response.PaymentCancelResponse;
import shop.bookbom.shop.domain.payment.dto.response.PaymentResponse;
import shop.bookbom.shop.domain.payment.exception.PaymentCancelFailedException;
import shop.bookbom.shop.domain.payment.exception.PaymentFailException;

@Component
@RequiredArgsConstructor
public class PaymentAdapterImpl implements PaymentAdapter {
    private final TossPayConfig tossPayConfig;

    /**
     * tosspay에게 결제 승인 요청 보냄
     *
     * @param paymentRequest
     * @return PaymentResponse
     */
    public PaymentResponse requestPaymentConfirm(PaymentRequest paymentRequest) {
        //secretkey를 가지고 authorization 만듬
        String secretKey = tossPayConfig.getSecretKey() + ":";
        String authorization = Base64.getEncoder().encodeToString(secretKey.getBytes());
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        //header에 authorization 넣음
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

    @Override
    public PaymentCancelResponse cancelPayment(String paymentKey, PaymentCancelRequest paymentCancelRequest) {
        //secretkey를 가지고 authorization 만듬
        String secretKey = tossPayConfig.getSecretKey() + ":";
        String authorization = Base64.getEncoder().encodeToString(secretKey.getBytes());
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        //header에 authorization 넣음
        headers.add("Authorization", "Basic " + authorization);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentCancelRequest> requestEntity =
                new HttpEntity<>(paymentCancelRequest, headers);

        PaymentCancelResponse response =
                restTemplate.exchange("https://api.tosspayments.com/v1/payments/{paymentKey}/cancel",
                        HttpMethod.POST,
                        requestEntity,
                        PaymentCancelResponse.class,
                        paymentKey).getBody();

        if (response == null) {
            throw new PaymentCancelFailedException();
        }
        return response;
    }
}
