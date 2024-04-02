package shop.bookbom.shop.domain.cartitem.entity;

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
import lombok.Setter;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.cart.entity.Cart;
import shop.bookbom.shop.domain.cartitem.exception.CartItemInvalidQuantityException;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Builder
    public CartItem(int quantity, Cart cart, Book book) {
        this.quantity = quantity;
        this.cart = cart;
        this.book = book;
    }

    public void addQuantity(int quantity) {
        int update = this.quantity + quantity;
        if (update < 1) {
            throw new CartItemInvalidQuantityException();
        }
        this.quantity = update;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }
}
