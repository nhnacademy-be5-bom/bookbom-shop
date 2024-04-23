package shop.bookbom.shop.domain.book.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import shop.bookbom.shop.domain.author.dto.AuthorDTO;
import shop.bookbom.shop.domain.file.dto.FileDTO;
import shop.bookbom.shop.domain.pointrate.dto.PointRateSimpleInformation;
import shop.bookbom.shop.domain.publisher.dto.PublisherSimpleInformation;
import shop.bookbom.shop.domain.review.dto.BookReviewStatisticsInformation;
import shop.bookbom.shop.domain.review.dto.ReviewSimpleInformation;
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

@NoArgsConstructor
public class BookMediumResponse {
    // 베스트도서/검색 페이지에서 사용하는 적절한 정보 응답 DTO
    // id, 표지, 제목, 작가, 출판사, 출판일자, 포인트, 가격, 할인가격, 설명 , 별점, 리뷰갯수
    @Getter
    private Long id;
    @Getter
    private String title;
    @Getter
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pubDate;
    @Getter
    private Integer cost;
    @Getter
    private Integer discountCost;
    @Getter
    private PublisherSimpleInformation publisher;
    @Getter
    private PointRateSimpleInformation pointRate;

    // 중복제거를 위해 야매로 hashset으로 설정
    // getter 따로 만듬, 사용 시 list 리턴
    private Map<Long, AuthorDTO> authors = new HashMap<>();
    private Map<Long, TagDTO> tags = new HashMap<>();
    private Map<Long, FileDTO> files = new HashMap<>();
    private Map<Long, ReviewSimpleInformation> reviews = new HashMap<>();

    // 리뷰 평점, 리뷰 총갯수
    @Getter
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
                              Map<Long, AuthorDTO> authors,
                              Map<Long, TagDTO> tags,
                              Map<Long, FileDTO> files,
                              Map<Long, ReviewSimpleInformation> reviews) {
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

        setReviewStatistics();
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

    public List<AuthorDTO> getAuthors() {
        if (this.authors == null) {
            return Collections.emptyList();
        } else {
            return new ArrayList<>(this.authors.values());
        }
    }

    public List<TagDTO> getTags() {
        if (this.tags == null) {
            return Collections.emptyList();
        } else {
            return new ArrayList<>(this.tags.values());
        }
    }

    public List<FileDTO> getFiles() {
        if (this.files == null) {
            return Collections.emptyList();
        } else {
            return new ArrayList<>(this.files.values());
        }
    }

    public List<ReviewSimpleInformation> getReviews() {
        if (this.reviews == null) {
            return Collections.emptyList();
        } else {
            return new ArrayList<>(this.reviews.values());
        }
    }

    private void setReviewStatistics() {
        Integer totalCount = 0;
        Double averageRate = 0D;

        if (this.reviews.size() != 1 && !this.reviews.containsKey(null)) {
            totalCount = this.reviews.size();
        }

        for (ReviewSimpleInformation review : this.reviews.values()) {
            averageRate += review.getRate();
        }

        this.reviewStatistics = BookReviewStatisticsInformation.builder()
                .totalReviewCount(totalCount)
                .averageReviewRate(totalCount == 0 ? averageRate : (averageRate / totalCount))
                .build();
    }

    private void fillMapFields(Map<Long, AuthorDTO> authors,
                               Map<Long, TagDTO> tags,
                               Map<Long, FileDTO> files,
                               Map<Long, ReviewSimpleInformation> review) {
        this.authors = authors;
        this.tags = tags;
        this.files = files;
        this.reviews = review;

        setReviewStatistics();
    }

}
