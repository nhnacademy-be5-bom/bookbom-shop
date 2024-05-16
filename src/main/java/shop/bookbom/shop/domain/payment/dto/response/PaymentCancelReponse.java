package shop.bookbom.shop.domain.payment.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
@Setter
public class PaymentCancelReponse {
    private String status;
}
