package shop.bookbom.shop.domain.book.dto.response;

import static shop.bookbom.shop.domain.book.DtoToListHandler.getThumbnailUrlFrom;
import static shop.bookbom.shop.domain.book.DtoToListHandler.processAuthors;
import static shop.bookbom.shop.domain.book.DtoToListHandler.processBookCategoriesToString;
import static shop.bookbom.shop.domain.book.DtoToListHandler.processTagsToString;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import shop.bookbom.shop.domain.author.dto.AuthorDTO;
import shop.bookbom.shop.domain.book.entity.Book;

@Getter
public class BookUpdateResponse {
    private Long id;
    private String thumbnail;
    private String title;
    private String description;
    private String index;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pubDate;
    private String isbn10;
    private String isbn13;
    private Integer cost;
    private Integer discountCost;
    private Boolean packagable;
    private String status;
    private Integer stock;

    private Long publisherId;
    private String publisherName;
    private List<AuthorDTO> author;
    private List<String> tags;
    private List<String> categories;

    @Builder
    private BookUpdateResponse(Long id, String thumbnail, String title, String description, String index,
                               LocalDate pubDate,
                               String isbn10, String isbn13, Integer cost, Integer discountCost, Boolean packagable
            , String status, Integer stock,
                               Long publisherId, String publisherName, List<AuthorDTO> author, List<String> tags,
                               List<String> categories) {
        this.id = id;
        this.thumbnail = thumbnail;
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
        this.publisherId = publisherId;
        this.publisherName = publisherName;
        this.author = author;
        this.tags = tags;
        this.categories = categories;
    }

    public static BookUpdateResponse of(Book book) {

        if (book == null) {
            return null;
        } else {
            return BookUpdateResponse.builder()
                    .id(book.getId())
                    .thumbnail(getThumbnailUrlFrom(book.getBookFiles()))
                    .title(book.getTitle())
                    .description(book.getDescription())
                    .index(book.getIndex())
                    .pubDate(book.getPubDate())
                    .isbn10(book.getIsbn10())
                    .isbn13(book.getIsbn13())
                    .cost(book.getCost())
                    .discountCost(book.getDiscountCost())
                    .packagable(book.getPackagable())
                    .status(book.getStatus().name())
                    .stock(book.getStock())
                    .publisherId(book.getPublisher().getId())
                    .publisherName(book.getPublisher().getName())
                    .author(processAuthors(book.getAuthors()))
                    .tags(processTagsToString(book.getTags()))
                    .categories(processBookCategoriesToString(book.getCategories()))
                    .build();
        }
    }
}
