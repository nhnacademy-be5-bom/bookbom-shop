package shop.bookbom.shop.domain.payment.controller;

import java.time.temporal.TemporalAmount;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.payment.dto.PaymentResponse;
import shop.bookbom.shop.domain.payment.dto.TossPayConfig;
import shop.bookbom.shop.domain.payment.service.PaymentService;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final TossPayConfig tossPayConfig;

    @GetMapping("/toss/success")
    public CommonResponse<PaymentResponse> tossPaymentSuccess(@RequestParam String paymentKey,
                                                              @RequestParam String orderId,
                                                              @RequestParam Integer amount) {
        new PaymentResponse(paymentKey, amount, orderId);
        return CommonResponse<>
    }
}
