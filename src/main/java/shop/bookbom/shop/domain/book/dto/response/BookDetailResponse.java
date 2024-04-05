package shop.bookbom.shop.domain.book.dto.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import shop.bookbom.shop.domain.bookauthor.entity.BookAuthor;
import shop.bookbom.shop.domain.bookcategory.entity.BookCategory;
import shop.bookbom.shop.domain.booktag.entity.BookTag;
import shop.bookbom.shop.domain.pointrate.entity.PointRate;
import shop.bookbom.shop.domain.publisher.entity.Publisher;

@Getter
public class BookDetailResponse {
    // 책 상세페이지 조회에 사용하는 책 단건 조회 응답 DTO
    // Book 의 필드 중 view, status 는 상세 페이지에서 사용하지 않으므로 제외
    private Long id;
    private String title;
    private String description;
    private String index;
    private LocalDate pubDate;
    private String isbn10;
    private String isbn13;
    private Integer cost;
    private Integer discountCost;
    private Boolean packagable;
    private Integer stock;
    private Publisher publisher;
    private PointRate pointRate;
    private List<BookAuthor> authors = new ArrayList<>();
    private List<BookTag> tags = new ArrayList<>();
    private List<BookCategory> categories = new ArrayList<>();

    @Builder
    public BookDetailResponse(Long id,
                              String title,
                              String description,
                              String index,
                              LocalDate pubDate,
                              String isbn10,
                              String isbn13,
                              Integer cost,
                              Integer discountCost,
                              Boolean packagable,
                              Integer stock,
                              Publisher publisher,
                              PointRate pointRate,
                              List<BookAuthor> authors,
                              List<BookTag> tags,
                              List<BookCategory> categories) {
        
        this.id = id;
        this.title = title;
        this.description = description;
        this.index = index;
        this.pubDate = pubDate;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.cost = cost;
        this.discountCost = discountCost;
        this.packagable = packagable;
        this.stock = stock;
        this.publisher = publisher;
        this.pointRate = pointRate;
        this.authors = authors;
        this.tags = tags;
        this.categories = categories;
    }
}
