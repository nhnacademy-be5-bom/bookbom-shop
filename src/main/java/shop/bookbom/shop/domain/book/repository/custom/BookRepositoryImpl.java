package shop.bookbom.shop.domain.book.repository.custom;


import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.querydsl.core.group.GroupBy.map;

import com.querydsl.core.types.Projections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.bookbom.shop.domain.author.dto.AuthorDTO;
import shop.bookbom.shop.domain.author.entity.QAuthor;
import shop.bookbom.shop.domain.book.dto.response.BookDetailResponse;
import shop.bookbom.shop.domain.book.dto.response.BookMediumResponse;
import shop.bookbom.shop.domain.book.dto.response.BookSimpleResponse;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.entity.BookStatus;
import shop.bookbom.shop.domain.book.entity.QBook;
import shop.bookbom.shop.domain.book.exception.BookNotFoundException;
import shop.bookbom.shop.domain.bookauthor.entity.BookAuthor;
import shop.bookbom.shop.domain.bookauthor.entity.QBookAuthor;
import shop.bookbom.shop.domain.bookcategory.entity.QBookCategory;
import shop.bookbom.shop.domain.bookfile.entity.BookFile;
import shop.bookbom.shop.domain.bookfile.entity.QBookFile;
import shop.bookbom.shop.domain.bookfiletype.entity.QBookFileType;
import shop.bookbom.shop.domain.booktag.entity.BookTag;
import shop.bookbom.shop.domain.booktag.entity.QBookTag;
import shop.bookbom.shop.domain.category.dto.CategoryDTO;
import shop.bookbom.shop.domain.category.entity.QCategory;
import shop.bookbom.shop.domain.file.entity.QFile;
import shop.bookbom.shop.domain.file.entity.dto.FileDTO;
import shop.bookbom.shop.domain.pointrate.dto.PointRateSimpleInformation;
import shop.bookbom.shop.domain.pointrate.entity.QPointRate;
import shop.bookbom.shop.domain.publisher.dto.PublisherSimpleInformation;
import shop.bookbom.shop.domain.publisher.entity.QPublisher;
import shop.bookbom.shop.domain.review.dto.ReviewSimpleInformation;
import shop.bookbom.shop.domain.review.entity.QReview;
import shop.bookbom.shop.domain.review.entity.Review;
import shop.bookbom.shop.domain.tag.dto.TagDTO;
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
    QPublisher publisher = QPublisher.publisher;
    QPointRate pointRate = QPointRate.pointRate;
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
    public BookDetailResponse getBookDetailInfoById(Long bookId) {

        List<BookDetailResponse> result = from(book)
                .join(book.publisher)
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

                .where(book.id.eq(bookId).and(book.status.ne(BookStatus.DEL)))
                .transform(groupBy(book.id).list(
                        Projections.constructor(BookDetailResponse.class,
                                book.id,
                                book.title,
                                book.description,
                                book.index,
                                book.pubDate,
                                book.isbn10,
                                book.isbn13,
                                book.cost,
                                book.discountCost,
                                book.packagable,
                                book.stock,
                                Projections.constructor(PublisherSimpleInformation.class, book.publisher.name),
                                Projections.constructor(PointRateSimpleInformation.class,
                                        book.pointRate.earnType.stringValue(),
                                        book.pointRate.earnPoint),
                                list(Projections.constructor(AuthorDTO.class, author.id, bookAuthor.role, author.name)),
                                list(Projections.constructor(TagDTO.class, tag.id, tag.name)),
                                list(Projections.constructor(CategoryDTO.class, category.id, category.name)),
                                list(Projections.constructor(FileDTO.class, file.url, file.extension))
                        )
                ));

        if (result.size() == 1) {// 항상 1
            return result.get(0);
        } else {// 중복된 결과 조회 오류
            throw new BookNotFoundException();
        }
    }

    @Override
    public BookMediumResponse getBookMediumInfoById(Long bookId) {
        List<BookMediumResponse> result = from(book)
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

                .where(book.id.eq(bookId).and(book.status.ne(BookStatus.DEL)))
                .transform(groupBy(book.id).list(
                        Projections.constructor(BookMediumResponse.class,
                                book.id,
                                book.title,
                                book.pubDate,
                                book.cost,
                                book.discountCost,
                                Projections.constructor(PublisherSimpleInformation.class, book.publisher.name),
                                Projections.constructor(PointRateSimpleInformation.class,
                                        book.pointRate.earnType.stringValue(),
                                        book.pointRate.earnPoint),
                                map(author.id, Projections.constructor(AuthorDTO.class, author.id, bookAuthor.role,
                                        author.name)),
                                map(tag.id, Projections.constructor(TagDTO.class, tag.id, tag.name)),
                                map(file.id, Projections.constructor(FileDTO.class, file.url, file.extension)),
                                map(review.id, Projections.constructor(ReviewSimpleInformation.class, review.id,
                                        review.rate, review.content)
                                )
                        )));

        if (result.size() == 1) {// 항상 1
            return result.get(0);
        } else {// #todo 중복된 결과 조회 오류로 변경
            throw new BookNotFoundException();
        }
    }

    @Override
    public BookSimpleResponse getBookSimpleInfoById(Long bookId) {

        List<BookSimpleResponse> result = from(book)
                .join(book.pointRate)

                .leftJoin(book.bookFiles, bookFiles)
                .leftJoin(bookFiles.file, file)
                .leftJoin(bookFiles.bookFileType, fileType)

                .where(book.id.eq(bookId).and(book.status.ne(BookStatus.DEL)))
                .transform(groupBy(book.id).list(
                        Projections.constructor(BookSimpleResponse.class,
                                book.id,
                                book.title,
                                book.cost,
                                book.discountCost,
                                Projections.constructor(PointRateSimpleInformation.class,
                                        book.pointRate.earnType.stringValue(),
                                        book.pointRate.earnPoint),
                                list(Projections.constructor(FileDTO.class, file.url, file.extension))
                        )
                ));

        if (result.size() == 1) {// 항상 1
            return result.get(0);
        } else {// 중복된 결과 조회 오류
            throw new BookNotFoundException();
        }
    }

    @Override
    public Book findByIdFetch(Long bookId) {
        return from(book)
                .where(book.id.eq(bookId)).fetchOne();
    }

    @Override
    public Page<BookMediumResponse> getPageableAndOrderByViewCountListBookMediumInfos(Pageable pageable) {
        List<BookMediumResponse> orderdList = getAllBookMediumInfosOrderByViewCount(pageable);
        long count = getTotalCount();

        return new PageImpl<>(orderdList, pageable, count);
    }

    @Override
    public Page<BookMediumResponse> getPageableListBookMediumInfos(Pageable pageable) {
        List<BookMediumResponse> mediumList = getAllBookMediumInfos(pageable);
        long count = getTotalCount();

        return new PageImpl<>(mediumList, pageable, count);
    }

    @Override
    public Page<BookMediumResponse> getPageableBookMediumInfosByCategoryId(Long categoryId, Pageable pageable) {
        List<BookMediumResponse> entityList = getListBookMediumInfosByCategoryId(categoryId, pageable);
        long count = getCount(categoryId);

        return new PageImpl<>(entityList, pageable, count);
    }

    private List<BookMediumResponse> getAllBookMediumInfosOrderByViewCount(Pageable pageable) {
        List<Book> entityList = from(book)
                .where(book.status.ne(BookStatus.DEL))
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())  // 페이지 사이즈
                .orderBy(book.views.desc())
                .select(book)
                .fetch();

        return convertBookToMedium(entityList);
    }

    private List<BookMediumResponse> getAllBookMediumInfos(Pageable pageable) {
        List<Book> entityList = from(book)
                .where(book.status.ne(BookStatus.DEL))
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())  // 페이지 사이즈
                .select(book)
                .fetch();

        return convertBookToMedium(entityList);
    }

    private List<BookMediumResponse> getListBookMediumInfosByCategoryId(Long categoryId, Pageable pageable) {
        List<Book> entityList = from(category).rightJoin(bookCategory).on(category.id.eq(bookCategory.category.id))
                .leftJoin(bookCategory.book)
                .where(category.id.eq(categoryId).and(bookCategory.book.status.ne(BookStatus.DEL)))
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())  // 페이지 사이즈
                .groupBy(bookCategory.book.id)
                .select(bookCategory.book)
                .fetch();

        return convertBookToMedium(entityList);
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

    private List<BookMediumResponse> convertBookToMedium(List<Book> bookList) {
        List<BookMediumResponse> responseList = new ArrayList<>();

        for (Book entity : bookList) {
            HashMap<Long, AuthorDTO> authors = new HashMap<>();
            HashMap<Long, TagDTO> tags = new HashMap<>();
            HashMap<Long, FileDTO> files = new HashMap<>();
            HashMap<Long, ReviewSimpleInformation> reviews = new HashMap<>();

            for (BookAuthor element : entity.getAuthors()) {
                authors.put(element.getAuthor().getId(), AuthorDTO.builder()
                        .id(element.getAuthor().getId())
                        .role(element.getRole())
                        .name(element.getAuthor().getName())
                        .build());
            }

            for (BookTag element : entity.getTags()) {
                tags.put(element.getTag().getId(), TagDTO.builder()
                        .id(element.getTag().getId())
                        .name(element.getTag().getName())
                        .build());
            }

            for (BookFile element : entity.getBookFiles()) {
                files.put(element.getFile().getId(), FileDTO.builder()
                        .url(element.getFile().getUrl())
                        .extension(element.getBookFileType().getName())
                        .build());
            }

            for (Review element : entity.getReviews()) {
                reviews.put(element.getId(), ReviewSimpleInformation.builder()
                        .id(element.getId())
                        .rate(element.getRate())
                        .content(element.getContent())
                        .build());
            }

            responseList.add(
                    BookMediumResponse.builder()
                            .id(entity.getId())
                            .title(entity.getTitle())
                            .pubDate(entity.getPubDate())
                            .cost(entity.getCost())
                            .discountCost(entity.getDiscountCost())
                            .publisher(
                                    PublisherSimpleInformation.builder()
                                            .name(entity.getPublisher().getName())
                                            .build()
                            )
                            .pointRate(
                                    PointRateSimpleInformation.builder()
                                            .earnType(entity.getPointRate().getEarnType().getValue())
                                            .earnPoint(entity.getPointRate().getEarnPoint())
                                            .build()
                            )
                            .authors(authors)
                            .tags(tags)
                            .files(files)
                            .review(reviews)
                            .build()
            );
        }


        return responseList;
    }
}
