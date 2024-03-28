package shop.bookbom.shop.ordercoupon.entity;

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
import shop.bookbom.shop.coupon.entity.Coupon;
import shop.bookbom.shop.order.entity.Order;

@Entity
@Table(name = "order_coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_book_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Builder
    public OrderCoupon(Order order, Coupon coupon) {
        this.order = order;
        this.coupon = coupon;
    }
}
