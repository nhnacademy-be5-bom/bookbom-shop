package shop.bookbom.shop.book.entity;

import java.time.LocalDate;
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
import shop.bookbom.shop.pointrate.entity.PointRate;
import shop.bookbom.shop.publisher.entity.Publisher;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")

    private Long id;

    private String title;

    private String description;

    @Column(name = "book_index")
    private String index;

    @Column(name = "pub_date")
    private LocalDate pubDate;

    @Column(name = "isbn_10")
    private String isbn10;

    @Column(name = "isbn_13")
    private String isbn13;

    private Integer cost;


    private Integer discountCost;

    private Boolean packagable;

    private Long views;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    private Integer stock;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_rate_id")
    private PointRate pointRate;

    @Builder

    public Book(
            String title,
            String description,
            String index,
            LocalDate pubDate,
            String isbn10,
            String isbn13,
            Integer cost,
            Integer discountCost,
            Boolean packagable,
            Long views,
            BookStatus status,
            Integer stock,
            Publisher publisher,
            PointRate pointRate
    ) {
        this.title = title;
        this.description = description;
        this.index = index;
        this.pubDate = pubDate;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.cost = cost;

        this.discountCost = discountCost;
        this.packagable = packagable;
        this.views = views;
        this.status = status;
        this.stock = stock;
        this.publisher = publisher;
        this.pointRate = pointRate;
    }
}
