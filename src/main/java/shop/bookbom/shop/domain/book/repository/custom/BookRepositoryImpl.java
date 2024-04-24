package shop.bookbom.shop.domain.book.repository.custom;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import shop.bookbom.shop.domain.bookauthor.entity.BookAuthor;
import shop.bookbom.shop.domain.bookauthor.entity.QBookAuthor;
import shop.bookbom.shop.domain.bookcategory.entity.QBookCategory;
import shop.bookbom.shop.domain.bookfile.entity.BookFile;
import shop.bookbom.shop.domain.bookfile.entity.QBookFile;
import shop.bookbom.shop.domain.bookfiletype.entity.QBookFileType;
import shop.bookbom.shop.domain.booktag.entity.BookTag;
import shop.bookbom.shop.domain.booktag.entity.QBookTag;
import shop.bookbom.shop.domain.category.entity.QCategory;
import shop.bookbom.shop.domain.file.dto.FileDTO;
import shop.bookbom.shop.domain.file.entity.QFile;
import shop.bookbom.shop.domain.pointrate.dto.PointRateSimpleInformation;
import shop.bookbom.shop.domain.publisher.dto.PublisherSimpleInformation;
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
    public Book findByIdFetch(Long bookId) {
        return from(book).fetchJoin()
                .where(book.id.eq(bookId))
                .fetchOne();
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
            List<AuthorDTO> authors = new ArrayList<>();
            List<TagDTO> tags = new ArrayList<>();
            List<FileDTO> files = new ArrayList<>();
            List<ReviewSimpleInformation> reviews = new ArrayList<>();

            for (BookAuthor element : entity.getAuthors()) {
                authors.add(AuthorDTO.builder()
                        .id(element.getAuthor().getId())
                        .role(element.getRole())
                        .name(element.getAuthor().getName())
                        .build());
            }

            for (BookTag element : entity.getTags()) {
                tags.add(TagDTO.builder()
                        .id(element.getTag().getId())
                        .name(element.getTag().getName())
                        .build());
            }

            for (BookFile element : entity.getBookFiles()) {
                files.add(FileDTO.builder()
                        .url(element.getFile().getUrl())
                        .extension(element.getBookFileType().getName())
                        .build());
            }

            for (Review element : entity.getReviews()) {
                reviews.add(ReviewSimpleInformation.builder()
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
                            .reviews(reviews)
                            .build()
            );
        }

        return responseList;
    }
}
