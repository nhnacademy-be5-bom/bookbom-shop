package shop.bookbom.shop.domain.book.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import shop.bookbom.shop.domain.author.dto.AuthorResponse;

@Getter
public class BookSearchResponse {
    private Long id;
    private String thumbnail;
    private String title;
    private List<AuthorResponse> author;
    private Long publisherId;
    private String publisherName;
    private LocalDate pubDate;
    private int price;
    private int discountPrice;
    private double reviewRating;
    private long reviewCount;

    @Builder
    public BookSearchResponse(
            Long id,
            String thumbnail,
            String title,
            List<AuthorResponse> author,
            Long publisherId,
            String publisherName,
            LocalDate pubDate,
            int price,
            int discountPrice,
            double reviewRating,
            long reviewCount
    ) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.title = title;
        this.author = author;
        this.publisherId = publisherId;
        this.publisherName = publisherName;
        this.pubDate = pubDate;
        this.price = price;
        this.discountPrice = discountPrice;
        this.reviewRating = reviewRating;
        this.reviewCount = reviewCount;
    }
}
