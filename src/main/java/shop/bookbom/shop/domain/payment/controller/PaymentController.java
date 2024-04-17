package shop.bookbom.shop.domain.payment.controller;

import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.order.entity.Order;
import shop.bookbom.shop.domain.payment.config.TossPayConfig;
import shop.bookbom.shop.domain.payment.dto.PaymentRequest;
import shop.bookbom.shop.domain.payment.dto.PaymentResponse;
import shop.bookbom.shop.domain.payment.dto.PaymentSuccessResponse;
import shop.bookbom.shop.domain.payment.entity.Payment;
import shop.bookbom.shop.domain.payment.service.PaymentService;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final TossPayConfig tossPayConfig;


    @PostMapping("/payment/tosspay/confirm")
    public CommonResponse<PaymentSuccessResponse> getPaymentConfirm(@RequestBody PaymentRequest paymentRequest) {


        String authorization = Base64.getEncoder().encodeToString(tossPayConfig.getSecretKey().getBytes());
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + authorization);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentRequest> requestEntity = new HttpEntity<>(paymentRequest, headers);

        ResponseEntity<PaymentResponse> responseEntity =
                restTemplate.exchange(tossPayConfig.getConfirmUrl(),
                        HttpMethod.POST,
                        requestEntity,
                        PaymentResponse.class);

        PaymentResponse paymentResponse = responseEntity.getBody();
        Order order = paymentService.verifyRequest(paymentResponse.getOrderId(), paymentResponse.getTotalAmount());
        Payment payment = paymentService.savePaymentInfo(paymentResponse, order);
        PaymentSuccessResponse paymentSuccessResponse = paymentService.orderComplete(payment);


        return CommonResponse.successWithData(paymentSuccessResponse);
    }
}
