package shop.bookbom.shop.domain.order.dto.response;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.bookbom.shop.domain.order.entity.Order;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderInfoResponse {
    private Long id;
    private LocalDate orderDate;
    private String orderNumber;
    private String name;
    private int totalCost;
    private String status;

    public static OrderInfoResponse of(Order order) {
        return new OrderInfoResponse(
                order.getId(),
                order.getOrderDate().toLocalDate(),
                order.getOrderNumber(),
                order.getOrderInfo(),
                order.getTotalCost(),
                order.getStatus().getName()
        );
    }
}
