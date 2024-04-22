package shop.bookbom.shop.domain.payment.controller;

import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.order.entity.Order;
import shop.bookbom.shop.domain.payment.config.TossPayConfig;
import shop.bookbom.shop.domain.payment.dto.PaymentRequest;
import shop.bookbom.shop.domain.payment.dto.PaymentResponse;
import shop.bookbom.shop.domain.payment.dto.PaymentSuccessResponse;
import shop.bookbom.shop.domain.payment.entity.Payment;
import shop.bookbom.shop.domain.payment.exception.PaymentAlreadyProcessed;
import shop.bookbom.shop.domain.payment.exception.PaymentFailException;
import shop.bookbom.shop.domain.payment.exception.PaymentNotFoundException;
import shop.bookbom.shop.domain.payment.service.PaymentService;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final TossPayConfig tossPayConfig;


    @Transactional
    @PostMapping("/payment/tosspay/confirm")
    public CommonResponse<PaymentSuccessResponse> getPaymentConfirm(@RequestBody PaymentRequest paymentRequest) {


        String authorization = Base64.getEncoder().encodeToString(tossPayConfig.getSecretKey().getBytes());
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + authorization);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentRequest> requestEntity = new HttpEntity<>(paymentRequest, headers);
        ResponseEntity<PaymentResponse> responseEntity;
        try {
            responseEntity =
                    restTemplate.exchange(tossPayConfig.getConfirmUrl(),
                            HttpMethod.POST,
                            requestEntity,
                            PaymentResponse.class);
            // 응답의 상태코드 확인 등 추가적인 작업 수행 가능
        } catch (HttpStatusCodeException e) {
            // 예외 처리: HTTP 상태 코드에 따라 다른 동작 수행
            HttpStatus statusCode = e.getStatusCode();

            if (statusCode == HttpStatus.NOT_FOUND) {
                // 404 에러 처리
                throw new PaymentNotFoundException();
            } else if (statusCode == HttpStatus.BAD_REQUEST) {
                // 400 에러 처리
                throw new PaymentAlreadyProcessed();
            } else {
                // 기타 에러 처리
                throw new PaymentFailException();
            }
        }

        PaymentResponse paymentResponse = responseEntity.getBody();
        Order order = paymentService.verifyRequest(paymentResponse.getOrderId(), paymentResponse.getTotalAmount());
        Payment payment = paymentService.savePaymentInfo(paymentResponse, order);
        PaymentSuccessResponse paymentSuccessResponse = paymentService.orderComplete(payment);


        return CommonResponse.successWithData(paymentSuccessResponse);
    }
}
