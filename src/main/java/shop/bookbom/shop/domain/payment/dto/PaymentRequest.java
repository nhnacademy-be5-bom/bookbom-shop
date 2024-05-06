package shop.bookbom.shop.domain.payment.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    @NotBlank
    private String paymentKey;
    @NotBlank
    private Integer amount;
    @NotBlank
    private String orderId;
}
