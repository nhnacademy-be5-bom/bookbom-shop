package shop.bookbom.shop.domain.couponbook.entity;

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
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.coupon.entity.Coupon;

@Entity
@Table(name = "coupon_book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_book_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Builder
    public CouponBook(Coupon coupon, Book book) {
        this.coupon = coupon;
        this.book = book;
    }
}
