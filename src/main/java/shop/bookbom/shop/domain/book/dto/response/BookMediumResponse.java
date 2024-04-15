package shop.bookbom.shop.domain.book.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.author.dto.AuthorDTO;
import shop.bookbom.shop.domain.file.entity.dto.FileDTO;
import shop.bookbom.shop.domain.pointrate.dto.PointRateSimpleInformation;
import shop.bookbom.shop.domain.publisher.dto.PublisherSimpleInformation;
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
@Getter
@NoArgsConstructor
public class BookMediumResponse {
    // 베스트도서/검색 페이지에서 사용하는 적절한 정보 응답 DTO
    // 표지, 제목, 작가, 출판사, 출판일자, 포인트, 가격, 할인가격, 설명 , 별점, 리뷰갯수

    private String title;
    private LocalDate pubDate;
    private Integer cost;
    private Integer discountCost;
    private PublisherSimpleInformation publisher;
    private PointRateSimpleInformation pointRate;
    private List<AuthorDTO> authors = new ArrayList<>();
    private List<TagDTO> tags = new ArrayList<>();
    private List<FileDTO> files = new ArrayList<>();
    private ReviewSimpleInformation review;

    @Builder
    @QueryProjection
    public BookMediumResponse(String title,
                              LocalDate pubDate,
                              Integer cost,
                              Integer discountCost,
                              PublisherSimpleInformation publisher,
                              PointRateSimpleInformation pointRate,
                              List<AuthorDTO> authors,
                              List<TagDTO> tags,
                              List<FileDTO> files,
                              ReviewSimpleInformation review) {
        this.title = title;
        this.pubDate = pubDate;
        this.cost = cost;
        this.discountCost = discountCost;
        this.publisher = publisher;
        this.pointRate = pointRate;
        this.authors = authors;
        this.tags = tags;
        this.files = files;
        this.review = review;
    }

}
