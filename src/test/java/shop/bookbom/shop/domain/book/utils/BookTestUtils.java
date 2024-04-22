package shop.bookbom.shop.domain.book.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import shop.bookbom.shop.domain.author.dto.AuthorDTO;
import shop.bookbom.shop.domain.book.dto.response.BookDetailResponse;
import shop.bookbom.shop.domain.book.dto.response.BookMediumResponse;
import shop.bookbom.shop.domain.book.dto.response.BookSimpleResponse;
import shop.bookbom.shop.domain.category.dto.CategoryDTO;
import shop.bookbom.shop.domain.file.entity.dto.FileDTO;
import shop.bookbom.shop.domain.pointrate.dto.PointRateSimpleInformation;
import shop.bookbom.shop.domain.publisher.dto.PublisherSimpleInformation;
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
                .authors(getAuthorDTOMap(getAuthorDTOs()))
                .tags(getTagDTOMap(getTagDTOs()))
                .files(getFileDTOMap(getFileDTOs()))
                .reviews(getReviewDTOMap(getReviewDTOs()))
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

    public static HashMap<Long, AuthorDTO> getAuthorDTOMap(List<AuthorDTO> authorList) {
        HashMap<Long, AuthorDTO> authorMap = new HashMap<>();
        for (AuthorDTO author : authorList) {
            authorMap.put(author.getId(), author);
        }
        return authorMap;
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

    public static HashMap<Long, TagDTO> getTagDTOMap(List<TagDTO> tagList) {
        HashMap<Long, TagDTO> tagMap = new HashMap<>();
        for (TagDTO tag : tagList) {
            tagMap.put(tag.getId(), tag);
        }
        return tagMap;
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

    public static HashMap<Long, CategoryDTO> getCategoryDTOMap(List<CategoryDTO> categoryList) {
        HashMap<Long, CategoryDTO> categoryMap = new HashMap<>();
        for (CategoryDTO category : categoryList) {
            categoryMap.put(category.getId(), category);
        }
        return categoryMap;
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

    public static HashMap<Long, FileDTO> getFileDTOMap(List<FileDTO> fileList) {
        HashMap<Long, FileDTO> fileMap = new HashMap<>();
        Long id = 0L;
        for (FileDTO file : fileList) {
            fileMap.put(++id, file);
        }
        return fileMap;
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

    public static HashMap<Long, ReviewSimpleInformation> getReviewDTOMap(List<ReviewSimpleInformation> reviewList) {
        HashMap<Long, ReviewSimpleInformation> reviewMap = new HashMap<>();
        for (ReviewSimpleInformation review : reviewList) {
            reviewMap.put(review.getId(), review);
        }
        return reviewMap;
    }

    public static PointRateSimpleInformation getPointRate() {
        return PointRateSimpleInformation.builder()
                .earnType("도서")
                .earnPoint(5)
                .build();
    }

    public static PublisherSimpleInformation getPublisher() {
        return PublisherSimpleInformation.builder()
                .name("출판사1")
                .build();
    }
}
