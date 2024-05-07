package shop.bookbom.shop.domain.book.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHitsImpl;
import org.springframework.data.elasticsearch.core.TotalHitsRelation;
import org.springframework.test.util.ReflectionTestUtils;
import shop.bookbom.shop.domain.author.dto.AuthorDTO;
import shop.bookbom.shop.domain.author.dto.AuthorResponse;
import shop.bookbom.shop.domain.author.dto.AuthorSimpleInfo;
import shop.bookbom.shop.domain.book.document.BookDocument;
import shop.bookbom.shop.domain.book.dto.BookSearchResponse;
import shop.bookbom.shop.domain.book.dto.request.BookAddRequest;
import shop.bookbom.shop.domain.book.dto.request.BookUpdateRequest;
import shop.bookbom.shop.domain.book.dto.response.BookDetailResponse;
import shop.bookbom.shop.domain.book.dto.response.BookMediumResponse;
import shop.bookbom.shop.domain.book.dto.response.BookSimpleResponse;
import shop.bookbom.shop.domain.book.entity.BookStatus;
import shop.bookbom.shop.domain.bookfiletype.entity.BookFileType;
import shop.bookbom.shop.domain.category.dto.CategoryDTO;
import shop.bookbom.shop.domain.category.entity.Category;
import shop.bookbom.shop.domain.category.entity.Status;
import shop.bookbom.shop.domain.file.dto.FileDTO;
import shop.bookbom.shop.domain.pointrate.dto.PointRateSimpleInformation;
import shop.bookbom.shop.domain.pointrate.entity.ApplyPointType;
import shop.bookbom.shop.domain.pointrate.entity.EarnPointType;
import shop.bookbom.shop.domain.pointrate.entity.PointRate;
import shop.bookbom.shop.domain.publisher.dto.PublisherSimpleInformation;
import shop.bookbom.shop.domain.publisher.entity.Publisher;
import shop.bookbom.shop.domain.review.dto.ReviewSimpleInformation;
import shop.bookbom.shop.domain.tag.dto.TagDTO;

/**
 * packageName    : shop.bookbom.shop.domain.book.utils
 * fileName       : BookTestUtils
 * author         : UuLaptop
 * date           : 2024-04-17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-17        UuLaptop       최초 생성
 */
public class BookTestUtils {
    ObjectMapper mapper = new ObjectMapper();

    private BookTestUtils() {

    }

    public static BookDetailResponse getBookDetailResponse(Long id, String title) {
        BookDetailResponse response = BookDetailResponse.builder()
                .id(id)
                .title(title)
                .description("설명")
                .index("인덱스")
                .pubDate(LocalDate.now())
                .isbn10("10")
                .isbn13("13")
                .cost(9999)
                .discountCost(777)
                .packagable(false)
                .stock(444)
                .publisher(getPublisher())
                .pointRate(getPointRate())
                .authors(getAuthorDTOs())
                .tags(getTagDTOs())
                .categories(getCategoryDTOs())
                .files(getFileDTOs())
                .build();

        return response;
    }

    public static BookMediumResponse getBookMediumResponse(Long id, String title) {
        BookMediumResponse response = BookMediumResponse.builder()
                .id(id)
                .title(title)
                .pubDate(LocalDate.now())
                .cost(9999)
                .discountCost(777)
                .publisher(getPublisher())
                .pointRate(getPointRate())
                .authors(getAuthorDTOs())
                .tags(getTagDTOs())
                .files(getFileDTOs())
                .reviews(getReviewDTOs())
                .build();
        return response;
    }

    public static BookSimpleResponse getBookSimpleResponse(Long id, String title) {
        BookSimpleResponse response = BookSimpleResponse.builder()
                .id(id)
                .title(title)
                .cost(9999)
                .discountCost(777)
                .pointRate(getPointRate())
                .files(getFileDTOs())
                .build();
        return response;
    }

    public static BookAddRequest getBookAddRequest(String title) {
        ArrayList<AuthorSimpleInfo> authors = new ArrayList<>();
        authors.add(new AuthorSimpleInfo("지은이", "전석준"));
        authors.add(new AuthorSimpleInfo("옮긴이", "전재학"));

        return new BookAddRequest(
                title,
                new ArrayList<>(List.of(new String[] {"카테1", "카테2"})),
                new ArrayList<>(List.of(new String[] {"태그1", "태그2"})),
                authors,
                "출판사",
                LocalDate.now(),
                "설명",
                "목차",
                "10",
                "13",
                1000,
                100,
                true,
                BookStatus.FOR_SALE,
                123
        );
    }

    public static BookUpdateRequest getBookUpdateRequest(String title) {

        return new BookUpdateRequest(1L,
                title,
                new ArrayList<>(List.of(new String[] {"카테1", "카테2"})),
                new ArrayList<>(List.of(new String[] {"태그1", "태그2"})),
                getAuthorDTOs(),
                "출판사",
                LocalDate.now(),
                "설명",
                "목차",
                "10",
                "13",
                1000,
                100,
                true,
                BookStatus.FOR_SALE,
                123
        );
    }

    public static List<AuthorDTO> getAuthorDTOs() {
        List<AuthorDTO> authors = new ArrayList<>();

        AuthorDTO author = AuthorDTO.builder()
                .id(1L)
                .role("지은이")
                .name("전석준")
                .build();
        authors.add(author);

        AuthorDTO author2 = AuthorDTO.builder()
                .id(2L)
                .role("옮긴이")
                .name("전재학")
                .build();
        authors.add(author2);

        return authors;
    }

    public static List<TagDTO> getTagDTOs() {
        List<TagDTO> tags = new ArrayList<>();

        TagDTO tag = TagDTO.builder()
                .id(1L)
                .name("신나는")
                .build();
        tags.add(tag);

        TagDTO tag2 = TagDTO.builder()
                .id(2L)
                .name("지겨운")
                .build();
        tags.add(tag2);

        TagDTO tag3 = TagDTO.builder()
                .id(3L)
                .name("태그3")
                .build();
        tags.add(tag3);

        return tags;

    }

    public static List<CategoryDTO> getCategoryDTOs() {
        List<CategoryDTO> categories = new ArrayList<>();

        CategoryDTO category = CategoryDTO.builder()
                .id(1L)
                .name("1단계")
                .build();
        categories.add(category);

        CategoryDTO category2 = CategoryDTO.builder()
                .id(2L)
                .name("2단계")
                .build();
        categories.add(category2);

        return categories;
    }

    public static Category getCategoryEntity() {
        Category category = Category.builder()
                .name("카테고리")
                .status(Status.USED)
                .parent(null)
                .build();
        ReflectionTestUtils.setField(category, "id", 1L);
        return category;
    }

    public static List<FileDTO> getFileDTOs() {
        List<FileDTO> files = new ArrayList<>();

        FileDTO file = FileDTO.builder()
                .url("img_url")
                .extension("png")
                .build();
        files.add(file);

        return files;
    }

    public static BookFileType getBookFileTypeEntity() {
        BookFileType bookFileType = BookFileType.builder()
                .name("img")
                .build();
        ReflectionTestUtils.setField(bookFileType, "id", 1L);
        return bookFileType;
    }

    public static List<ReviewSimpleInformation> getReviewDTOs() {
        List<ReviewSimpleInformation> reviews = new ArrayList<>();

        ReviewSimpleInformation file = ReviewSimpleInformation.builder()
                .id(0L)
                .rate(1)
                .content("good")
                .build();
        reviews.add(file);

        ReviewSimpleInformation file2 = ReviewSimpleInformation.builder()
                .id(1L)
                .rate(2)
                .content("bad")
                .build();
        reviews.add(file2);

        ReviewSimpleInformation file3 = ReviewSimpleInformation.builder()
                .id(2L)
                .rate(3)
                .content("soso")
                .build();
        reviews.add(file3);

        return reviews;
    }

    public static PointRateSimpleInformation getPointRate() {
        return PointRateSimpleInformation.builder()
                .earnType("도서")
                .earnPoint(5)
                .build();
    }

    public static PointRate getPointRateEntity() {
        PointRate pointRate = PointRate.builder()
                .name("포인트적립")
                .earnType(EarnPointType.RATE)
                .earnPoint(5)
                .applyType(ApplyPointType.BOOK)
                .createdAt(LocalDateTime.now())
                .build();

        ReflectionTestUtils.setField(pointRate, "id", 2L);

        return pointRate;
    }

    public static PublisherSimpleInformation getPublisher() {
        return PublisherSimpleInformation.builder()
                .name("출판사1")
                .build();
    }

    public static Publisher getPublisherEntity() {
        Publisher publisher = Publisher.builder()
                .name("출판사")
                .build();
        ReflectionTestUtils.setField(publisher, "id", 1L);
        return publisher;
    }

    public static BookDocument getBookDocument() {
        return new BookDocument(
                1L,
                "title",
                "description",
                "index",
                10000,
                9000,
                LocalDate.now(),
                "1",
                "role",
                "name",
                1L,
                "publisher",
                0,
                1L,
                "thumbnail",
                "jpg");
    }


    public static SearchHitsImpl<BookDocument> getSearchHits(BookDocument bookDocument) {
        SearchHit<BookDocument> searchHit = new SearchHit<>(
                "book_index", // 인덱스 이름
                "1", // 문서 ID
                null, // 라우팅 값
                1.0f, // 점수
                null, // 정렬 값
                null, // 하이라이트 필드
                null, // 내부 히트
                null, // 중첩 메타데이터
                null, // 설명
                null, // 일치하는 쿼리
                bookDocument // 내용
        );
        return new SearchHitsImpl<>(
                1L, // 총 히트 수
                TotalHitsRelation.EQUAL_TO, // 히트 수 관계
                1.0f, // 최대 점수
                null, // 스크롤 ID
                List.of(searchHit), // 검색 히트 리스트
                null, // 집계 정보
                null  // 제안 정보
        );
    }

    public static BookSearchResponse getBookSearchResponse() {
        return new BookSearchResponse(
                1L,
                "thumbnail",
                "title",
                List.of(new AuthorResponse(1L, "name", "지은이")),
                1L,
                "publisher",
                LocalDate.now(),
                10000,
                8000,
                4.5,
                10L
        );
    }

}
