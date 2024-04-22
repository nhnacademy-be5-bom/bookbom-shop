package shop.bookbom.shop.domain.order.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.orderstatus.entity.OrderStatus;
import shop.bookbom.shop.domain.users.entity.User;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long id;

    @Column(name = "order_number", nullable = false, length = 64)
    private String orderNumber;

    @Column(name = "order_info", nullable = false, length = 100)
    private String orderInfo;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "name", nullable = false, length = 50)
    private String senderName;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String senderPhoneNumber;

    @Column(name = "total_cost", nullable = false, columnDefinition = "int default 0")
    private Integer totalCost;

    @Column(name = "used_point", nullable = false, columnDefinition = "int default 0")
    private int usedPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_status_id", nullable = false)
    private OrderStatus status;

    @Builder
    public Order(
            String orderNumber,
            String orderInfo,
            LocalDateTime orderDate,
            String senderName,
            String senderPhoneNumber,
            Integer totalCost,
            int usedPoint,
            User user,
            OrderStatus status
    ) {
        this.orderNumber = orderNumber;
        this.orderInfo = orderInfo;
        this.orderDate = orderDate;
        this.senderName = senderName;
        this.senderPhoneNumber = senderPhoneNumber;
        this.totalCost = totalCost;
        this.usedPoint = usedPoint;
        this.user = user;
        this.status = status;
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }


    public void updateOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
