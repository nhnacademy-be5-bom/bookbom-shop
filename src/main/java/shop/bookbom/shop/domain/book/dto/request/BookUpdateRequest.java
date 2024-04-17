package shop.bookbom.shop.domain.book.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import shop.bookbom.shop.domain.author.dto.AuthorDTO;
import shop.bookbom.shop.domain.book.entity.BookStatus;

@Getter
public class BookUpdateRequest {
    // 관리자 책 수정에 사용하는 책 수정 요청 DTO
    // Book 의 필드 중 view 는 수정 페이지에서 사용하지 않으므로 제외

    private Long bookId;
    @JsonIgnore
    private MultipartFile thumbnail;
    private String title;
    private List<String> categories;
    private List<String> tags;
    private List<AuthorDTO> authors;
    private String publisher;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pubDate;

    private String description;
    private String index;
    private String isbn10;
    private String isbn13;
    private Integer cost;
    private Integer discountCost;
    private Boolean packagable;
    private BookStatus status;
    private Integer stock;

    @Builder
    public BookUpdateRequest(Long bookId,
                             MultipartFile thumbnail,
                             String title,
                             List<String> categories,
                             List<String> tags,
                             List<AuthorDTO> authors,
                             String publisher,
                             String description,
                             String index,
                             LocalDate pubDate,
                             String isbn10,
                             String isbn13,
                             Integer cost,
                             Integer discountCost,
                             Boolean packagable,
                             BookStatus status,
                             Integer stock) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.categories = categories;
        this.tags = tags;
        this.authors = authors;
        this.publisher = publisher;
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
    }
}
