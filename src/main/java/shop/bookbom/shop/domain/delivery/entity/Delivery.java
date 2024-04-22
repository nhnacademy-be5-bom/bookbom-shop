package shop.bookbom.shop.domain.delivery.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.deliveryaddress.entity.DeliveryAddress;
import shop.bookbom.shop.domain.order.entity.Order;

@Entity
@Table(name = "delivery")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Delivery {
    @Id
    @Column(name = "order_id")
    private Long id;

    @MapsId
    @JoinColumn(name = "order_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Order order;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_address_id", nullable = false)
    private DeliveryAddress deliveryAddress;

    @Column(name = "delivery_cost", nullable = false)
    private Integer cost;

    @Column(name = "estimated_date", nullable = false)
    private LocalDate estimatedDate;

    @Column(name = "complete_date")
    private LocalDate completeDate;

    @Builder
    public Delivery(Order order, String name, String phoneNumber, DeliveryAddress deliveryAddress, Integer cost,
                    LocalDate estimatedDate, LocalDate completeDate) {
        this.order = order;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.deliveryAddress = deliveryAddress;
        this.cost = cost;
        this.estimatedDate = estimatedDate;
        this.completeDate = completeDate;
    }

    public void complete(LocalDate completeDate) {
        this.completeDate = completeDate;
    }
}
