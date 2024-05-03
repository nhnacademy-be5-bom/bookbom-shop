package shop.bookbom.shop.domain.order.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.bookbom.shop.domain.delivery.dto.response.DeliveryAddressDto;
import shop.bookbom.shop.domain.order.entity.Order;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    private Long id;
    private LocalDateTime orderDate;
    private String orderNumber;
    private String orderInfo;
    private List<OrderBookResponse> books;
    private String senderName;
    private String senderPhoneNumber;
    private String recipientName;
    private String recipientPhoneNumber;
    private DeliveryAddressDto recipientAddress;
    private int totalPrice;
    private int discountPrice;
    private int paymentPrice;
    private int accumulatedPoint;

    public static OrderDetailResponse of(Order order, List<OrderBookResponse> orderBooks) {
        return new OrderDetailResponse(
                order.getId(),
                order.getOrderDate(),
                order.getOrderNumber(),
                order.getOrderInfo(),
                orderBooks,
                order.getSenderName(),
                order.getSenderPhoneNumber(),
                order.getDelivery().getName(),
                order.getDelivery().getPhoneNumber(),
                DeliveryAddressDto.of(order.getDelivery().getDeliveryAddress()),
                order.getTotalCost(),
                order.getDiscountCost(),
                order.getPayment().getCost(),
                order.getUsedPoint()
        );
    }
}
