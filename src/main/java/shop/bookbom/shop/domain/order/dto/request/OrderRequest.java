package shop.bookbom.shop.domain.order.dto.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequest {
    @Valid
    private List<WrapperSelectBookRequest> wrapperSelectRequestList;
    @NotBlank
    private String name;
    @NotBlank
    private String phoneNumber;
    @NotNull
    private Integer totalCost;
    @NotNull
    @Min(value = 1, message = "은 최소 1원입니다")
    private Integer discountCost;
    private String estimatedDateTostring;
    @NotNull
    private Integer deliveryCost;
    @NotBlank
    private String zipCode;
    @NotBlank
    private String deliveryAddress;
    @NotBlank
    private String addressDetail;
    @NotNull
    private Integer usedPoint;
    @NotNull
    private Integer usedCouponCost;
    @NotNull
    private Long couponId;
}

