package shop.bookbom.shop.domain.order.dto.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OpenOrderRequest {
    @Valid
    private List<WrapperSelectBookRequest> wrapperSelectRequestList;
    @NotBlank
    private String name;
    @NotBlank
    private String phoneNumber;
    @NotNull
    private Integer totalCost;
    @NotNull
    private Integer discountCost;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String estimatedDateTostring;
    @NotNull
    private Integer deliveryCost;
    @NotBlank
    private String zipCode;
    @NotBlank
    private String deliveryAddress;
    @NotBlank
    private String addressDetail;

}
