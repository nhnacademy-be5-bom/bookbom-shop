package shop.bookbom.shop.domain.book.repository.custom;


import static shop.bookbom.shop.domain.book.DtoToListHandler.getThumbnailUrlFrom;
import static shop.bookbom.shop.domain.book.DtoToListHandler.processReviews;
import static shop.bookbom.shop.domain.bookfiletype.entity.QBookFileType.bookFileType;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
import shop.bookbom.shop.domain.book.dto.response.BookUpdateResponse;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.entity.BookStatus;
import shop.bookbom.shop.domain.book.entity.QBook;
import shop.bookbom.shop.domain.book.exception.ExceedOffsetLimitException;
import shop.bookbom.shop.domain.bookauthor.entity.BookAuthor;
import shop.bookbom.shop.domain.bookauthor.entity.QBookAuthor;
import shop.bookbom.shop.domain.bookcategory.entity.QBookCategory;
import shop.bookbom.shop.domain.bookfile.entity.QBookFile;
import shop.bookbom.shop.domain.bookfiletype.entity.QBookFileType;
import shop.bookbom.shop.domain.booktag.entity.QBookTag;
import shop.bookbom.shop.domain.category.entity.QCategory;
import shop.bookbom.shop.domain.file.entity.QFile;
import shop.bookbom.shop.domain.publisher.entity.QPublisher;
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
    private final JPAQueryFactory queryFactory;
    QBook book = QBook.book;
    QAuthor author = QAuthor.author;
    QPublisher publisher = QPublisher.publisher;
    QBookAuthor bookAuthor = QBookAuthor.bookAuthor;
    QTag tag = QTag.tag;
    QBookTag bookTag = QBookTag.bookTag;
    QCategory category = QCategory.category;
    QBookCategory bookCategory = QBookCategory.bookCategory;
    QFile file = QFile.file;
    QBookFileType fileType = bookFileType;
    QBookFile bookFiles = QBookFile.bookFile;
    QReview review = QReview.review;
    public static final int BEST_LIMIT = 100;

    public BookRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Book.class);
        this.queryFactory = queryFactory;
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
    public Optional<BookUpdateResponse> getBookUpdateInfoById(Long bookId) {

        Book bookEntity = from(book)
                .where(book.id.eq(bookId))
                .select(book)
                .fetchOne();

        BookUpdateResponse response = BookUpdateResponse.of(bookEntity);

        return Optional.ofNullable(response);
    }

    @Override
    public Page<BookSearchResponse> getPageableAndOrderByViewCountListBookMediumInfos(Pageable pageable) {
        List<BookSearchResponse> orderdList = getAllBookMediumInfosOrderByViewCount(pageable);

        return new PageImpl<>(orderdList, pageable, BEST_LIMIT);
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

    @Override
    public Page<BookSearchResponse> getPageableListBookMediumInfosOrderByDate(Pageable pageable) {
        long limit = pageable.getPageSize();
        if ((pageable.getOffset() + pageable.getPageSize()) > BEST_LIMIT) {
            limit = BEST_LIMIT - pageable.getOffset();
        }

        List<Book> result = from(book)
                .where(book.status.ne(BookStatus.DEL))
                .offset(pageable.getOffset())
                .limit(limit)
                .orderBy(book.pubDate.desc())
                .select(book)
                .fetch();

        List<BookSearchResponse> content = convertBookToSearch(result);

        Long countQuery = queryFactory.select(book.count())
                .from(book)
                .where(book.status.ne(BookStatus.DEL))
                .limit(limit)
                .fetchOne();

        long count = countQuery == null ? 0L : countQuery;
        if (count >= BEST_LIMIT) {
            count = BEST_LIMIT;
        }
        return new PageImpl<>(content, pageable, count);
    }

    private List<BookSearchResponse> getAllBookMediumInfosOrderByViewCount(Pageable pageable) {

        List<Book> entityList;

        JPQLQuery<Book> query = from(book)
                .where(book.status.ne(BookStatus.DEL))
                .orderBy(book.views.desc())
                .select(book);

        if ((pageable.getOffset() + pageable.getPageSize()) > BEST_LIMIT) {

            if (BEST_LIMIT - pageable.getOffset() <= 0) {
                throw new ExceedOffsetLimitException();
            } else {
                entityList = query
                        .offset(pageable.getOffset())   // 페이지 번호
                        .limit(BEST_LIMIT - pageable.getOffset())  // 페이지 사이즈
                        .fetch();
            }

        } else {
            entityList = query
                    .offset(pageable.getOffset())   // 페이지 번호
                    .limit(pageable.getPageSize())  // 페이지 사이즈
                    .fetch();
        }

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
                entityList = query.orderBy(bookCategory.book.views.desc()).fetch();
                break;
            case "LATEST":
                entityList = query.orderBy(bookCategory.book.pubDate.desc()).fetch();
                break;
            case "LOWEST_PRICE":
                entityList = query.orderBy(bookCategory.book.discountCost.asc()).fetch();
                break;
            case "HIGHEST_PRICE":
                entityList = query.orderBy(bookCategory.book.discountCost.desc()).fetch();
                break;
            case "OLDEST":
                entityList = query.orderBy(bookCategory.book.pubDate.asc()).fetch();
                break;
            case "NONE":
                entityList = query.orderBy(bookCategory.book.title.asc()).fetch();
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
        return from(bookCategory)

                .where(bookCategory.category.id.eq(categoryId)
                        .and(bookCategory.book.status.ne(BookStatus.DEL)))

                .select(book.count())
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
                            .thumbnail(getThumbnailUrlFrom(entity.getBookFiles()))
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
