package shop.bookbom.shop.domain.order.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.delivery.entity.Delivery;
import shop.bookbom.shop.domain.orderbook.entity.OrderBook;
import shop.bookbom.shop.domain.orderstatus.entity.OrderStatus;
import shop.bookbom.shop.domain.payment.entity.Payment;
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

    @Column(name = "order_number", nullable = false, length = 32)
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

    @Column(name = "discount_cost", nullable = false)
    private Integer discountCost;

    @Column(name = "used_point", nullable = false)
    private int usedPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_status_id", nullable = false)
    private OrderStatus status;

    @OneToOne(mappedBy = "order")
    private Delivery delivery;

    @OneToOne(mappedBy = "order")
    private Payment payment;

    @OneToMany(mappedBy = "order")
    private List<OrderBook> orderBooks = new ArrayList<>();


    @Builder
    public Order(
            String orderNumber,
            String orderInfo,
            LocalDateTime orderDate,
            String senderName,
            String senderPhoneNumber,
            Integer totalCost,
            Integer discountCost,
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
        this.discountCost = discountCost;
        this.usedPoint = usedPoint;
        this.user = user;
        this.status = status;
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    public void updateUser(User user) {
        this.user = user;
    }

    public void updateOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
