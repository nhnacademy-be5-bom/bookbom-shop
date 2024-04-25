package shop.bookbom.shop.domain.book.repository.custom;


import static shop.bookbom.shop.domain.book.DtoToListHandler.getThumbnailFrom;
import static shop.bookbom.shop.domain.book.DtoToListHandler.processReviews;

import com.querydsl.jpa.JPQLQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.bookbom.shop.domain.author.dto.AuthorResponse;
import shop.bookbom.shop.domain.author.entity.QAuthor;
import shop.bookbom.shop.domain.book.dto.BookSearchResponse;
import shop.bookbom.shop.domain.book.dto.response.BookDetailResponse;
import shop.bookbom.shop.domain.book.dto.response.BookMediumResponse;
import shop.bookbom.shop.domain.book.dto.response.BookSimpleResponse;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.entity.BookStatus;
import shop.bookbom.shop.domain.book.entity.QBook;
import shop.bookbom.shop.domain.bookauthor.entity.BookAuthor;
import shop.bookbom.shop.domain.bookauthor.entity.QBookAuthor;
import shop.bookbom.shop.domain.bookcategory.entity.QBookCategory;
import shop.bookbom.shop.domain.bookfile.entity.QBookFile;
import shop.bookbom.shop.domain.bookfiletype.entity.QBookFileType;
import shop.bookbom.shop.domain.booktag.entity.QBookTag;
import shop.bookbom.shop.domain.category.entity.QCategory;
import shop.bookbom.shop.domain.file.entity.QFile;
import shop.bookbom.shop.domain.review.dto.BookReviewStatisticsInformation;
import shop.bookbom.shop.domain.review.entity.QReview;
import shop.bookbom.shop.domain.tag.entity.QTag;

/**
 * packageName    : shop.bookbom.shop.domain.book.repository.custom
 * fileName       : BookRepositoryCustomImpl
 * author         : 전석준
 * date           : 2024-04-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-12        전석준       최초 생성
 */
public class BookRepositoryImpl extends QuerydslRepositorySupport implements BookRepositoryCustom {
    QBook book = QBook.book;
    QAuthor author = QAuthor.author;
    QBookAuthor bookAuthor = QBookAuthor.bookAuthor;
    QTag tag = QTag.tag;
    QBookTag bookTag = QBookTag.bookTag;
    QCategory category = QCategory.category;
    QBookCategory bookCategory = QBookCategory.bookCategory;
    QFile file = QFile.file;
    QBookFileType fileType = QBookFileType.bookFileType;
    QBookFile bookFiles = QBookFile.bookFile;
    QReview review = QReview.review;

    public BookRepositoryImpl() {
        super(Book.class);
    }

    @Override
    public Optional<BookDetailResponse> getBookDetailInfoById(Long bookId) {

        Book bookEntity = from(book)
                .where(book.id.eq(bookId).and(book.status.ne(BookStatus.DEL)))
                .select(book)
                .fetchOne();

        BookDetailResponse response = BookDetailResponse.of(bookEntity,
                bookEntity.getAuthors(),
                bookEntity.getTags(),
                bookEntity.getCategories(),
                bookEntity.getBookFiles(),
                bookEntity.getReviews());

        return Optional.ofNullable(response);
    }

    @Override
    public Optional<BookMediumResponse> getBookMediumInfoById(Long bookId) {

        Book bookEntity = from(book)
                .where(book.id.eq(bookId).and(book.status.ne(BookStatus.DEL)))
                .select(book)
                .fetchOne();

        BookMediumResponse response = BookMediumResponse.of(bookEntity,
                bookEntity.getAuthors(),
                bookEntity.getTags(),
                bookEntity.getBookFiles(),
                bookEntity.getReviews());

        return Optional.ofNullable(response);
    }

    @Override
    public Optional<BookSimpleResponse> getBookSimpleInfoById(Long bookId) {

        Book bookEntity = from(book)
                .where(book.id.eq(bookId).and(book.status.ne(BookStatus.DEL)))
                .select(book)
                .fetchOne();

        BookSimpleResponse response = BookSimpleResponse.of(bookEntity, bookEntity.getBookFiles());

        return Optional.ofNullable(response);
    }

    @Override
    public Page<BookSearchResponse> getPageableAndOrderByViewCountListBookMediumInfos(Pageable pageable) {
        List<BookSearchResponse> orderdList = getAllBookMediumInfosOrderByViewCount(pageable);
        long count = getTotalCount();

        return new PageImpl<>(orderdList, pageable, count);
    }

    @Override
    public Page<BookSearchResponse> getPageableListBookMediumInfos(Pageable pageable) {
        List<BookSearchResponse> mediumList = getAllBookMediumInfos(pageable);
        long count = getTotalCount();

        return new PageImpl<>(mediumList, pageable, count);
    }

    @Override
    public Page<BookSearchResponse> getPageableBookMediumInfosByCategoryId(Long categoryId,
                                                                           String sortCondition,
                                                                           Pageable pageable) {
        List<BookSearchResponse> entityList = getListBookMediumInfosByCategoryId(categoryId, sortCondition, pageable);
        long count = getCount(categoryId);

        return new PageImpl<>(entityList, pageable, count);
    }

    private List<BookSearchResponse> getAllBookMediumInfosOrderByViewCount(Pageable pageable) {
        List<Book> entityList = from(book)
                .where(book.status.ne(BookStatus.DEL))
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())  // 페이지 사이즈
                .orderBy(book.views.desc())
                .select(book)
                .fetch();

        return convertBookToSearch(entityList);
    }

    private List<BookSearchResponse> getAllBookMediumInfos(Pageable pageable) {
        List<Book> entityList = from(book)
                .where(book.status.ne(BookStatus.DEL))
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())  // 페이지 사이즈
                .select(book)
                .fetch();

        return convertBookToSearch(entityList);
    }

    private List<BookSearchResponse> getListBookMediumInfosByCategoryId(Long categoryId,
                                                                        String sortCondition,
                                                                        Pageable pageable) {
        List<Book> entityList;

        JPQLQuery<Book> query = from(category).rightJoin(bookCategory).on(category.id.eq(bookCategory.category.id))
                .leftJoin(bookCategory.book)
                .where(category.id.eq(categoryId).and(bookCategory.book.status.ne(BookStatus.DEL)))
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())  // 페이지 사이즈
                .groupBy(bookCategory.book.id)
                .select(bookCategory.book);

        switch (sortCondition) {
            case "POPULAR":
                entityList = query.orderBy(book.views.desc()).fetch();
                break;
            case "LATEST":
                entityList = query.orderBy(book.pubDate.desc()).fetch();
                break;
            case "LOWEST_PRICE":
                entityList = query.orderBy(book.discountCost.asc()).fetch();
                break;
            case "HIGHEST_PRICE":
                entityList = query.orderBy(book.discountCost.desc()).fetch();
                break;
            case "OLDEST":
                entityList = query.orderBy(book.pubDate.asc()).fetch();
                break;
            case "NONE":
                entityList = query.orderBy(book.title.asc()).fetch();
                break;
            default:
                entityList = query.fetch();
                break;
        }

        return convertBookToSearch(entityList);
    }

    private long getTotalCount() {
        return from(book)
                .where(book.status.ne(BookStatus.DEL))
                .select(book.count())
                .distinct()
                .fetchOne();
    }

    private long getCount(Long categoryId) {
        return from(book)
                .join(book.pointRate)

                .leftJoin(book.authors, bookAuthor)
                .leftJoin(bookAuthor.author, author)

                .leftJoin(book.tags, bookTag)
                .leftJoin(bookTag.tag, tag)

                .leftJoin(book.categories, bookCategory)
                .leftJoin(bookCategory.category, category)

                .leftJoin(book.bookFiles, bookFiles)
                .leftJoin(bookFiles.file, file)
                .leftJoin(bookFiles.bookFileType, fileType)

                .leftJoin(book.reviews, review)

                .where(bookCategory.category.id.eq(categoryId).and(book.status.ne(BookStatus.DEL)))

                .select(book.count())
                .distinct()
                .fetchOne();
    }

    private List<BookSearchResponse> convertBookToSearch(List<Book> bookList) {
        List<BookSearchResponse> responseList = new ArrayList<>();

        for (Book entity : bookList) {
            List<AuthorResponse> authors = new ArrayList<>();

            for (BookAuthor element : entity.getAuthors()) {
                authors.add(AuthorResponse.builder()
                        .id(element.getAuthor().getId())
                        .role(element.getRole())
                        .name(element.getAuthor().getName())
                        .build());
            }

            BookReviewStatisticsInformation reviewStatistics = processReviews(entity.getReviews());

            responseList.add(
                    BookSearchResponse.builder()
                            .id(entity.getId())
                            .thumbnail(getThumbnailFrom(entity.getBookFiles()))
                            .title(entity.getTitle())
                            .author(authors)
                            .publisherId(entity.getPublisher().getId())
                            .publisherName(entity.getPublisher().getName())
                            .pubDate(entity.getPubDate())
                            .price(entity.getCost())
                            .discountPrice(entity.getDiscountCost())
                            .reviewRating(reviewStatistics.getAverageReviewRate())
                            .reviewCount(reviewStatistics.getTotalReviewCount())
                            .build()
            );
        }

        return responseList;
    }
}
