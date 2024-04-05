package shop.bookbom.shop.domain.book.dto.request;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import shop.bookbom.shop.domain.book.entity.BookStatus;
import shop.bookbom.shop.domain.bookauthor.entity.BookAuthor;
import shop.bookbom.shop.domain.pointrate.entity.PointRate;
import shop.bookbom.shop.domain.publisher.entity.Publisher;

@Getter
public class BookAddRequest {
    // 관리자 책 등록에 사용하는 책 등록 요청 DTO
    // Book 의 필드 중 id, view 는 등록 페이지에서 사용하지 않으므로 제외

    private String title;
    private String description;
    private String index;
    private LocalDate pubDate;
    private String isbn10;
    private String isbn13;
    private Integer cost;
    private Integer discountCost;
    private Boolean packagable;
    private BookStatus status;
    private Integer stock;
    private Publisher publisher;
    private PointRate pointRate;
    private List<BookAuthor> authors;

    @Builder
    public BookAddRequest(String title,
                          String description,
                          String index,
                          LocalDate pubDate,
                          String isbn10,
                          String isbn13,
                          Integer cost,
                          Integer discountCost,
                          Boolean packagable,
                          BookStatus status,
                          Integer stock,
                          Publisher publisher,
                          PointRate pointRate,
                          List<BookAuthor> authors) {
        
        this.title = title;
        this.description = description;
        this.index = index;
        this.pubDate = pubDate;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.cost = cost;
        this.discountCost = discountCost;
        this.packagable = packagable;
        this.status = status;
        this.stock = stock;
        this.publisher = publisher;
        this.pointRate = pointRate;
        this.authors = authors;
    }
}
