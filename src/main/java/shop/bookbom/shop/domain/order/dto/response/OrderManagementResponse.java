package shop.bookbom.shop.domain.order.dto.response;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.bookbom.shop.domain.order.entity.Order;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderManagementResponse {
    private Long id;
    private String orderNumber;
    private LocalDate orderDate;
    private String senderName;
    private String orderInfo;
    private LocalDate expectedDeliveryDate;
    private LocalDate completeDeliveryDate;
    private String status;


    public static OrderManagementResponse of(Order order) {
        return new OrderManagementResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getOrderDate().toLocalDate(),
                order.getSenderName(),
                order.getOrderInfo(),
                order.getDelivery().getEstimatedDate(),
                order.getDelivery().getCompleteDate(),
                order.getStatus().getName()
        );
    }
}
