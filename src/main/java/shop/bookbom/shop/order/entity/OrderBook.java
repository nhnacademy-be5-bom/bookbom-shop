package shop.bookbom.shop.order.entity;

import java.awt.print.Book;
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

@Entity
@Table(name = "order_book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_book_id")
    private Long id;

    @Column(name = "order_quantity")
    private int quantity;

    private boolean packaging;

    @Enumerated(EnumType.STRING)
    private OrderBookStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wrapper_id")
    private Wrapper wrapper;

    @Builder
    public OrderBook(int quantity, boolean packaging, OrderBookStatus status, Book book, Order order, Wrapper wrapper) {
        this.quantity = quantity;
        this.packaging = packaging;
        this.status = status;
        this.book = book;
        this.order = order;
        this.wrapper = wrapper;
    }

    public void updateStatus(OrderBookStatus status) {
        this.status = status;
    }
}
