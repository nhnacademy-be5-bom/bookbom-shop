package shop.bookbom.shop.domain.book.dto.response;

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
import shop.bookbom.shop.domain.bookcategory.entity.BookCategory;
import shop.bookbom.shop.domain.bookfile.entity.BookFile;
import shop.bookbom.shop.domain.booktag.entity.BookTag;
import shop.bookbom.shop.domain.category.dto.CategoryDTO;
import shop.bookbom.shop.domain.file.dto.FileDTO;
import shop.bookbom.shop.domain.pointrate.dto.PointRateSimpleInformation;
import shop.bookbom.shop.domain.publisher.dto.PublisherSimpleInformation;
import shop.bookbom.shop.domain.review.dto.BookReviewStatisticsInformation;
import shop.bookbom.shop.domain.review.entity.Review;
import shop.bookbom.shop.domain.tag.dto.TagDTO;

@Getter
@NoArgsConstructor
public class BookDetailResponse {
    // 책 상세페이지에 사용하는 최대 정보 응답 DTO
    // Book 의 필드 중 view, status 는 상세 페이지에서 사용하지 않으므로 제외
    private Long id;
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
    private Integer stock;
    private PublisherSimpleInformation publisher;
    private PointRateSimpleInformation pointRate;
    private List<AuthorDTO> authors = new ArrayList<>();
    private List<TagDTO> tags = new ArrayList<>();
    private List<CategoryDTO> categories = new ArrayList<>();
    private List<FileDTO> files = new ArrayList<>();
    private BookReviewStatisticsInformation reviewStatistics;

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
                              List<CategoryDTO> categories,
                              List<FileDTO> files,
                              BookReviewStatisticsInformation reviewStatistics) {
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
        this.files = files;
        this.reviewStatistics = reviewStatistics;
    }

    public static BookDetailResponse of(Book book,
                                        List<BookAuthor> authors,
                                        List<BookTag> tags,
                                        List<BookCategory> categories,
                                        List<BookFile> files,
                                        List<Review> reviews) {

        return BookDetailResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .index(book.getIndex())
                .pubDate(book.getPubDate())
                .isbn10(book.getIsbn10())
                .isbn13(book.getIsbn13())
                .cost(book.getCost())
                .discountCost(book.getDiscountCost())
                .packagable(book.getPackagable())
                .stock(book.getStock())
                .publisher(
                        PublisherSimpleInformation.builder()
                                .name(book.getPublisher().getName())
                                .build()
                )
                .pointRate(PointRateSimpleInformation.builder()
                        .earnType(book.getPointRate().getEarnType().getValue())
                        .earnPoint(book.getPointRate().getEarnPoint())
                        .build())
                .authors(processAuthors(authors))
                .tags(processTags(tags))
                .categories(processCategories(categories))
                .files(processFiles(files))
                .reviewStatistics(processReviews(reviews))
                .build();
    }

    private static List<AuthorDTO> processAuthors(List<BookAuthor> bookAuthors) {
        List<AuthorDTO> authorList = new ArrayList<>();
        for (BookAuthor bookAuthor : bookAuthors) {
            authorList.add(AuthorDTO.from(bookAuthor));
        }
        return authorList;
    }

    private static List<TagDTO> processTags(List<BookTag> bookTags) {
        List<TagDTO> tagList = new ArrayList<>();
        for (BookTag bookTag : bookTags) {
            tagList.add(TagDTO.from(bookTag));
        }
        return tagList;
    }

    private static List<CategoryDTO> processCategories(List<BookCategory> bookCategories) {
        List<CategoryDTO> categoryList = new ArrayList<>();
        for (BookCategory bookCategory : bookCategories) {
            categoryList.add(CategoryDTO.from(bookCategory));
        }
        return categoryList;
    }

    private static List<FileDTO> processFiles(List<BookFile> bookFiles) {
        List<FileDTO> fileList = new ArrayList<>();
        for (BookFile bookFile : bookFiles) {
            fileList.add(FileDTO.from(bookFile));
        }
        return fileList;
    }

    private static BookReviewStatisticsInformation processReviews(List<Review> reviews) {
        Integer totalCount = 0;
        Double averageRate = 0D;

        for (Review review : reviews) {
            totalCount++;
            averageRate += review.getRate();
        }

        return BookReviewStatisticsInformation.builder()
                .totalReviewCount(totalCount)
                .averageReviewRate(totalCount == 0 ? averageRate : (averageRate / totalCount))
                .build();
    }
}
