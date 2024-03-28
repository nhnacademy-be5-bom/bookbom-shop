package shop.bookbom.shop.payment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.order.entity.Order;

@Entity
@Table(name = "payment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "payment_cost")
    private int cost;

    @Column(name = "payment_key")
    private String key;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;


    @Builder
    public Payment(Order order, int cost, String key, PaymentMethod paymentMethod) {
        this.id = order.getId();
        this.order = order;
        this.cost = cost;
        this.key = key;
        this.paymentMethod = paymentMethod;
    }

}
