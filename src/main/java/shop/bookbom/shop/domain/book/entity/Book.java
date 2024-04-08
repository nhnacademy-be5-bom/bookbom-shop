package shop.bookbom.shop.domain.book.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.bookauthor.entity.BookAuthor;
import shop.bookbom.shop.domain.bookcategory.entity.BookCategory;
import shop.bookbom.shop.domain.bookfile.entity.BookFile;
import shop.bookbom.shop.domain.booktag.entity.BookTag;
import shop.bookbom.shop.domain.pointrate.entity.PointRate;
import shop.bookbom.shop.domain.publisher.entity.Publisher;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "book_index", columnDefinition = "TEXT", nullable = false)
    private String index;

    @Column(name = "pub_date", nullable = false)
    private LocalDate pubDate;

    @Column(name = "isbn_10", length = 10, nullable = false)
    private String isbn10;

    @Column(name = "isbn_13", length = 13, nullable = false)
    private String isbn13;

    @Column(nullable = false)
    private Integer cost;

    @Column(nullable = false)
    private Integer discountCost;

    @Column(nullable = false)
    private Boolean packagable;

    @Column(nullable = false)
    private Long views;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @Column(nullable = false)
    private Integer stock;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_rate_id", nullable = false)
    private PointRate pointRate;

    @OneToMany(mappedBy = "book")
    private List<BookAuthor> authors = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<BookTag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private List<BookCategory> categories = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<BookFile> bookFiles = new ArrayList<>();

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
