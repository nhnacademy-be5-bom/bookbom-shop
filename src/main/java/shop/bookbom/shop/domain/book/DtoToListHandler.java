package shop.bookbom.shop.domain.book;

import java.util.ArrayList;
import java.util.List;
import shop.bookbom.shop.common.file.exception.FileNotFoundException;
import shop.bookbom.shop.domain.author.dto.AuthorDTO;
import shop.bookbom.shop.domain.bookauthor.entity.BookAuthor;
import shop.bookbom.shop.domain.bookcategory.entity.BookCategory;
import shop.bookbom.shop.domain.bookfile.entity.BookFile;
import shop.bookbom.shop.domain.booktag.entity.BookTag;
import shop.bookbom.shop.domain.category.dto.CategoryDTO;
import shop.bookbom.shop.domain.category.dto.response.CategoryNameAndChildResponse;
import shop.bookbom.shop.domain.category.entity.Category;
import shop.bookbom.shop.domain.file.dto.FileDTO;
import shop.bookbom.shop.domain.review.dto.BookReviewStatisticsInformation;
import shop.bookbom.shop.domain.review.entity.Review;
import shop.bookbom.shop.domain.tag.dto.TagDTO;

/**
 * packageName    : shop.bookbom.shop.domain.book.dto
 * fileName       : DtoToListHandler
 * author         : UuLaptop
 * date           : 2024-04-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-24        UuLaptop       최초 생성
 */
public class DtoToListHandler {
    private DtoToListHandler() {
    }

    public static List<AuthorDTO> processAuthors(List<BookAuthor> bookAuthors) {
        List<AuthorDTO> authorList = new ArrayList<>();
        for (BookAuthor bookAuthor : bookAuthors) {
            authorList.add(AuthorDTO.from(bookAuthor));
        }
        return authorList;
    }

    public static List<TagDTO> processTags(List<BookTag> bookTags) {
        List<TagDTO> tagList = new ArrayList<>();
        for (BookTag bookTag : bookTags) {
            tagList.add(TagDTO.from(bookTag));
        }
        return tagList;
    }

    public static List<String> processTagsToString(List<BookTag> bookTags) {
        List<String> tagList = new ArrayList<>();
        for (BookTag bookTag : bookTags) {
            tagList.add(bookTag.getTag().getName());
        }
        return tagList;
    }

    public static List<CategoryDTO> processBookCategories(List<BookCategory> bookCategories) {
        List<CategoryDTO> categoryList = new ArrayList<>();
        for (BookCategory bookCategory : bookCategories) {
            categoryList.add(CategoryDTO.from(bookCategory));
        }
        return categoryList;
    }

    public static List<CategoryNameAndChildResponse> processCategories(List<Category> categories) {
        List<CategoryNameAndChildResponse> categoryList = new ArrayList<>();
        for (Category category : categories) {
            categoryList.add(CategoryNameAndChildResponse.from(category));
        }
        return categoryList;
    }

    public static List<String> processBookCategoriesToString(List<BookCategory> categories) {
        List<String> categoryList = new ArrayList<>();
        for (BookCategory category : categories) {
            categoryList.add(category.getCategory().getName());
        }
        return categoryList;
    }

    public static List<FileDTO> processFiles(List<BookFile> bookFiles) {
        List<FileDTO> fileList = new ArrayList<>();
        for (BookFile bookFile : bookFiles) {
            fileList.add(FileDTO.from(bookFile));
        }
        return fileList;
    }

    public static String getThumbnailUrlFrom(List<BookFile> bookFiles) {

        BookFile thumbnailBookFile = bookFiles.stream()
                .filter(bookFile -> "img".equals(bookFile.getBookFileType().getName()))
                .findFirst()
                .orElseThrow(FileNotFoundException::new);

        return thumbnailBookFile.getFile().getUrl();
    }

    public static BookFile getThumbnailBookFileFrom(List<BookFile> bookFiles) {

        return bookFiles.stream()
                .filter(bookFile -> "img".equals(bookFile.getBookFileType().getName()))
                .findFirst()
                .orElseThrow(FileNotFoundException::new);
    }

    public static BookReviewStatisticsInformation processReviews(List<Review> reviews) {
        int totalCount = 0;
        double averageRate = 0D;

        for (Review review : reviews) {
            totalCount++;
            averageRate += review.getRate();
        }
        double averageReviewRate = totalCount == 0 ? averageRate : (averageRate / totalCount);
        return BookReviewStatisticsInformation.builder()
                .totalReviewCount(totalCount)
                .averageReviewRate(Math.round(averageReviewRate * 10) / 10.0)
                .build();
    }
}
