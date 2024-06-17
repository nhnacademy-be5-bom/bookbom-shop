package shop.bookbom.shop.domain.book.dto.request;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import shop.bookbom.shop.domain.author.dto.AuthorDTO;
import shop.bookbom.shop.domain.book.entity.BookStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateRequest {
    private Long id;
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
}
