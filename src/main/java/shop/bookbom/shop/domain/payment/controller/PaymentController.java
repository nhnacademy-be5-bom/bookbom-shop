package shop.bookbom.shop.domain.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.payment.dto.PaymentRequest;
import shop.bookbom.shop.domain.payment.dto.PaymentSuccessResponse;
import shop.bookbom.shop.domain.payment.service.PaymentService;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;


    @Transactional
    @PostMapping("/payment/tosspay/confirm")
    public CommonResponse<PaymentSuccessResponse> getPaymentConfirm(@RequestBody PaymentRequest paymentRequest) {

        PaymentSuccessResponse paymentSuccessResponse = paymentService.getPaymnetConfirm(paymentRequest);

        return CommonResponse.successWithData(paymentSuccessResponse);
    }
}
