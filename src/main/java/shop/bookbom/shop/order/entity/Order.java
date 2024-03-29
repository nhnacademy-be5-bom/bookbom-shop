package shop.bookbom.shop.order.entity;

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
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.orderbook.entity.OrderBook;
import shop.bookbom.shop.orderstatus.entity.OrderStatus;
import shop.bookbom.shop.users.entity.User;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "order_info")
    private String orderInfo;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "name")
    private String senderName;

    @Column(name = "phone_number")
    private String senderPhoneNumber;

    @Column(name = "total_cost")
    private Long totalCost;

    @Column(name = "used_point")
    private int usedPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_status_id")
    private OrderStatus status;

    @OneToMany(mappedBy = "order")
    private List<OrderBook> orderBooks = new ArrayList<>();


    @Builder
    public Order(
            String orderNumber,
            String orderInfo,
            LocalDateTime orderDate,
            String senderName,
            String senderPhoneNumber,
            Long totalCost,
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

    public void addOrderBook(OrderBook orderBook) {
        orderBooks.add(orderBook);
    }
}
