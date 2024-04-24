package shop.bookbom.shop.domain.book.dto.response;

import static shop.bookbom.shop.domain.book.DtoToListHandler.processAuthors;
import static shop.bookbom.shop.domain.book.DtoToListHandler.processFiles;
import static shop.bookbom.shop.domain.book.DtoToListHandler.processReviews;
import static shop.bookbom.shop.domain.book.DtoToListHandler.processTags;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import shop.bookbom.shop.domain.author.dto.AuthorDTO;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.bookauthor.entity.BookAuthor;
import shop.bookbom.shop.domain.bookfile.entity.BookFile;
import shop.bookbom.shop.domain.booktag.entity.BookTag;
import shop.bookbom.shop.domain.file.dto.FileDTO;
import shop.bookbom.shop.domain.pointrate.dto.PointRateSimpleInformation;
import shop.bookbom.shop.domain.publisher.dto.PublisherSimpleInformation;
import shop.bookbom.shop.domain.review.dto.BookReviewStatisticsInformation;
import shop.bookbom.shop.domain.review.dto.ReviewSimpleInformation;
import shop.bookbom.shop.domain.review.entity.Review;
import shop.bookbom.shop.domain.tag.dto.TagDTO;

/**
 * packageName    : shop.bookbom.shop.domain.book.dto.response
 * fileName       : BookMediumResponse
 * author         : UuLaptop
 * date           : 2024-04-15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-15        UuLaptop       최초 생성
 */
@Getter
@NoArgsConstructor
public class BookMediumResponse {
    // 베스트도서/검색 페이지에서 사용하는 적절한 정보 응답 DTO
    // id, 표지, 제목, 작가, 출판사, 출판일자, 포인트, 가격, 할인가격, 설명 , 별점, 리뷰갯수
    private Long id;
    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pubDate;

    private Integer cost;
    private Integer discountCost;
    private PublisherSimpleInformation publisher;
    private PointRateSimpleInformation pointRate;
    private List<AuthorDTO> authors = new ArrayList<>();
    private List<TagDTO> tags = new ArrayList<>();
    private List<FileDTO> files = new ArrayList<>();
    private List<ReviewSimpleInformation> reviews = new ArrayList<>();
    // 리뷰 평점, 리뷰 총갯수
    private BookReviewStatisticsInformation reviewStatistics;

    @Builder
    @QueryProjection
    public BookMediumResponse(Long id,
                              String title,
                              LocalDate pubDate,
                              Integer cost,
                              Integer discountCost,
                              PublisherSimpleInformation publisher,
                              PointRateSimpleInformation pointRate,
                              List<AuthorDTO> authors,
                              List<TagDTO> tags,
                              List<FileDTO> files,
                              List<ReviewSimpleInformation> reviews,
                              BookReviewStatisticsInformation reviewStatistics) {
        this.id = id;
        this.title = title;
        this.pubDate = pubDate;
        this.cost = cost;
        this.discountCost = discountCost;
        this.publisher = publisher;
        this.pointRate = pointRate;
        this.authors = authors;
        this.tags = tags;
        this.files = files;
        this.reviews = reviews;
        this.reviewStatistics = reviewStatistics;
    }

    @Builder
    public BookMediumResponse(Long id,
                              String title,
                              LocalDate pubDate,
                              Integer cost,
                              Integer discountCost,
                              PublisherSimpleInformation publisher,
                              PointRateSimpleInformation pointRate) {
        this.id = id;
        this.title = title;
        this.pubDate = pubDate;
        this.cost = cost;
        this.discountCost = discountCost;
        this.publisher = publisher;
        this.pointRate = pointRate;
    }

    public static BookMediumResponse of(Book book,
                                        List<BookAuthor> authors,
                                        List<BookTag> tags,
                                        List<BookFile> files,
                                        List<Review> reviews) {
        return BookMediumResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .pubDate(book.getPubDate())
                .cost(book.getCost())
                .discountCost(book.getDiscountCost())
                .publisher(
                        PublisherSimpleInformation.builder()
                                .name(book.getPublisher().getName())
                                .build()
                )
                .pointRate(PointRateSimpleInformation.builder()
                        .earnType(book.getPointRate().getEarnType().getValue())
                        .earnPoint(book.getPointRate().getEarnPoint())
                        .build()
                )
                .authors(processAuthors(authors))
                .tags(processTags(tags))
                .files(processFiles(files))
                .reviewStatistics(processReviews(reviews))
                .build();
    }

}
