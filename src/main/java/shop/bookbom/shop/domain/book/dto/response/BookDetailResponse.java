package shop.bookbom.shop.domain.book.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.author.dto.AuthorDTO;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.bookauthor.entity.BookAuthor;
import shop.bookbom.shop.domain.category.dto.CategoryDTO;
import shop.bookbom.shop.domain.category.entity.Category;
import shop.bookbom.shop.domain.pointrate.dto.PointRateSimpleInformation;
import shop.bookbom.shop.domain.publisher.dto.PublisherSimpleInformation;
import shop.bookbom.shop.domain.tag.dto.TagDTO;
import shop.bookbom.shop.domain.tag.entity.Tag;

@Getter
@NoArgsConstructor
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
    private PublisherSimpleInformation publisher;
    private PointRateSimpleInformation pointRate;
    private List<AuthorDTO> authors = new ArrayList<>();
    private List<TagDTO> tags = new ArrayList<>();
    private List<CategoryDTO> categories = new ArrayList<>();

    @Builder
    @QueryProjection
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
                              PublisherSimpleInformation publisher,
                              PointRateSimpleInformation pointRate,
                              List<AuthorDTO> authors,
                              List<TagDTO> tags,
                              List<CategoryDTO> categories
    ) {
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

    @Builder
    @QueryProjection
    public BookDetailResponse(Book book,
                              List<BookAuthor> bookAuthorList,
                              List<Tag> tagList,
                              List<Category> categoryList) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.description = book.getDescription();
        this.index = book.getIndex();
        this.pubDate = book.getPubDate();
        this.isbn10 = book.getIsbn10();
        this.isbn13 = book.getIsbn13();
        this.cost = book.getCost();
        this.discountCost = book.getDiscountCost();
        this.packagable = book.getPackagable();
        this.stock = book.getStock();
        this.publisher = PublisherSimpleInformation.builder()
                .publisher(book.getPublisher())
                .build();
        this.pointRate = PointRateSimpleInformation.builder()
                .pointRate(book.getPointRate())
                .build();

        bookAuthorList.forEach(
                bookAuthor -> this.authors.add(
                        AuthorDTO.builder()
                                .id(bookAuthor.getAuthor().getId())
                                .name(bookAuthor.getAuthor().getName())
                                .role(bookAuthor.getRole())
                                .build()
                )
        );

        tagList.forEach(
                tag -> this.tags.add(
                        TagDTO.builder()
                                .id(tag.getId())
                                .name(tag.getName())
                                .build()
                )
        );

        categoryList.forEach(
                category -> this.categories.add(
                        CategoryDTO.builder()
                                .id(category.getId())
                                .name(category.getName())
                                .build()
                )
        );
    }
}

