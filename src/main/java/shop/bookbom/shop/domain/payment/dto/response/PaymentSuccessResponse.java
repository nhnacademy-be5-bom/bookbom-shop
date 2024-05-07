package shop.bookbom.shop.domain.payment.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.orderbook.dto.OrderBookInfoDto;

@Getter
@NoArgsConstructor
@Builder
public class PaymentSuccessResponse {
    private String orderNumber;
    private String orderInfo;
    private Integer totalCount;
    private List<OrderBookInfoDto> orderBookInfoDtoList;
    private Integer totalAmount;
    private String paymentMethodName;
    private String deliveryName;
    private String deliveryPhoneNumber;
    private String zipCode;
    private String deliveryAddress;
    private String addressDetail;

    public PaymentSuccessResponse(String orderNumber, String orderInfo, Integer totalCount,
                                  List<OrderBookInfoDto> orderBookInfoDtoList, Integer totalAmount,
                                  String paymentMethodName, String deliveryName, String deliveryPhoneNumber,
                                  String zipCode,
                                  String deliveryAddress, String addressDetail) {
        this.orderNumber = orderNumber;
        this.orderInfo = orderInfo;
        this.totalCount = totalCount;
        this.orderBookInfoDtoList = orderBookInfoDtoList;
        this.totalAmount = totalAmount;
        this.paymentMethodName = paymentMethodName;
        this.deliveryName = deliveryName;
        this.deliveryPhoneNumber = deliveryPhoneNumber;
        this.zipCode = zipCode;
        this.deliveryAddress = deliveryAddress;
        this.addressDetail = addressDetail;
    }
}
