package shop.bookbom.shop.domain.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.payment.dto.request.PaymentRequest;
import shop.bookbom.shop.domain.payment.dto.response.OrderIdResponse;
import shop.bookbom.shop.domain.payment.dto.response.PaymentSuccessResponse;
import shop.bookbom.shop.domain.payment.service.PaymentService;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    /**
     * 토스페이에 결제 승인 요청 보냄
     *
     * @param paymentRequest
     * @return
     */
    @Transactional
    @PostMapping("/payment/tosspay/confirm")
    public CommonResponse<OrderIdResponse> getPaymentConfirm(@RequestBody PaymentRequest paymentRequest) {

        OrderIdResponse orderIdResponse = paymentService.getPaymnetConfirm(paymentRequest);

        return CommonResponse.successWithData(orderIdResponse);
    }

    @Transactional
    @GetMapping("/payment/order-complete/{orderId}")
    public CommonResponse<PaymentSuccessResponse> completeOrder(@PathVariable(name = "orderId") Long orderId) {
        PaymentSuccessResponse paymentSuccessResponse = paymentService.orderComplete(orderId);
        return CommonResponse.successWithData(paymentSuccessResponse);
    }
}
