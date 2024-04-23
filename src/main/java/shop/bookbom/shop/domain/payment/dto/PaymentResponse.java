package shop.bookbom.shop.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class PaymentResponse {
    private String paymentKey;
    private String orderId;
    private String orderName;
    private String method;
    private Integer totalAmount;
    private String approvedAt;
    private CardDto card;
    private EasyPayDto easyPay;

    private FailureDto failure;

    public PaymentResponse(String paymentKey, String orderId, String orderName, String method, Integer totalAmount,
                           String approvedAt, CardDto card, EasyPayDto easyPay, FailureDto failure) {
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.orderName = orderName;
        this.method = method;
        this.totalAmount = totalAmount;
        this.approvedAt = approvedAt;
        this.card = card;
        this.easyPay = easyPay;
        this.failure = failure;
    }
}
