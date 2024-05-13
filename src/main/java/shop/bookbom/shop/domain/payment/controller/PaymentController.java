package shop.bookbom.shop.domain.payment.controller;

import lombok.RequiredArgsConstructor;
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
import shop.bookbom.shop.domain.payment.service.PaymentProcessService;
import shop.bookbom.shop.domain.payment.service.PaymentService;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final PaymentProcessService paymentProcessService;

    /**
     * 토스페이에 결제 승인 요청 보냄
     *
     * @param paymentRequest
     * @return
     */
    @PostMapping("/payment/tosspay/confirm")
    public CommonResponse<OrderIdResponse> getPaymentConfirm(@RequestBody PaymentRequest paymentRequest) {
        Long orderId = paymentProcessService.proceesPayment(paymentRequest);


        return CommonResponse.successWithData(OrderIdResponse.builder().orderId(orderId).build());
    }

    @PostMapping("/payment/free")
    public CommonResponse<OrderIdResponse> processFreePayment(@RequestBody String orderNumber) {
        Long orderId = paymentProcessService.processFreePayment(orderNumber);
        return CommonResponse.successWithData(OrderIdResponse.builder().orderId(orderId).build());
    }

    @GetMapping("/payment/order-complete/{orderId}")
    public CommonResponse<PaymentSuccessResponse> completeOrder(@PathVariable(name = "orderId") Long orderId) {

        PaymentSuccessResponse paymentSuccessResponse = paymentService.orderComplete(orderId);
        return CommonResponse.successWithData(paymentSuccessResponse);
    }

    @GetMapping("/payment/order-complete/free/{orderId}")
    public CommonResponse<PaymentSuccessResponse> completeFreeOrder(@PathVariable(name = "orderId") Long orderId) {

        PaymentSuccessResponse paymentSuccessResponse = paymentService.orderFreeComplete(orderId);
        return CommonResponse.successWithData(paymentSuccessResponse);
    }
}
