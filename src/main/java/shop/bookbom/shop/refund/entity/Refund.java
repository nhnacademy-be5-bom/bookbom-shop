package shop.bookbom.shop.refund.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import shop.bookbom.shop.orderbook.entity.OrderBook;
import shop.bookbom.shop.refundcategory.entity.RefundCategory;

@Entity
@Table(name = "refund")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refund_id")
    private Long id;

    private String reason;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refund_category_id")
    private RefundCategory refundCategory;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_book_id")
    private OrderBook orderBook;

    @Builder
    public Refund(String reason, int quantity, RefundCategory refundCategory, OrderBook orderBook) {
        this.reason = reason;
        this.quantity = quantity;
        this.refundCategory = refundCategory;
        this.orderBook = orderBook;
    }
}
