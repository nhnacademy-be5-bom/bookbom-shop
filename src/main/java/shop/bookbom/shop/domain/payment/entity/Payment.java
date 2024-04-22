package shop.bookbom.shop.domain.payment.entity;


import javax.persistence.CascadeType;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.order.entity.Order;
import shop.bookbom.shop.domain.paymentmethod.entity.PaymentMethod;

@Entity
@Table(name = "payment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Payment {
    @Id
    @Column(name = "order_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    @MapsId
    private Order order;

    @Column(name = "payment_cost", nullable = false)
    private int cost;

    @Column(name = "payment_key", nullable = false, length = 200)
    private String key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id", nullable = false)
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
