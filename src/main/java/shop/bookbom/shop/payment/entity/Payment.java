package shop.bookbom.shop.payment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", insertable = false, updatable = false)
    private Long orderId;

    @Column(name = "payment_cost")
    private Integer paymentCost;

    @Column(name = "payment_key")
    private String paymentKey;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Builder
    public Payment(Integer paymentCost, String paymentKey, PaymentMethod paymentMethod,
                   Order order) {
        this.paymentCost = paymentCost;
        this.paymentKey = paymentKey;
        this.paymentMethod = paymentMethod;
        this.order = order;
    }
}
