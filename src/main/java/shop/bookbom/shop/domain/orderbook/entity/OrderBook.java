package shop.bookbom.shop.domain.orderbook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.order.entity.Order;
import shop.bookbom.shop.domain.wrapper.entity.Wrapper;

@Entity
@Table(name = "order_book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_book_id", nullable = false)
    private Long id;

    @Column(name = "order_quantity", nullable = false)
    private int quantity;

    @Column(nullable = false)
    private boolean packaging;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderBookStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wrapper_id", nullable = false)
    private Wrapper wrapper;

    @Builder
    public OrderBook(
            int quantity,
            boolean packaging,
            OrderBookStatus status,
            Book book,
            Order order,
            int price,
            Wrapper wrapper
    ) {
        this.quantity = quantity;
        this.packaging = packaging;
        this.status = status;
        this.book = book;
        this.price = price;
        this.order = order;
        this.wrapper = wrapper;
    }

    public void updateStatus(OrderBookStatus status) {
        this.status = status;
    }
}
