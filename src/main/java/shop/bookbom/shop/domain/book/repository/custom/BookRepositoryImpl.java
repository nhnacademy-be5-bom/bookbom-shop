package shop.bookbom.shop.domain.book.repository.custom;


import static com.querydsl.core.group.GroupBy.avg;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.querydsl.core.types.ExpressionUtils.count;

import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.bookbom.shop.domain.author.dto.AuthorDTO;
import shop.bookbom.shop.domain.author.entity.QAuthor;
import shop.bookbom.shop.domain.book.dto.response.BookDetailResponse;
import shop.bookbom.shop.domain.book.dto.response.BookMediumResponse;
import shop.bookbom.shop.domain.book.dto.response.BookSimpleResponse;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.entity.QBook;
import shop.bookbom.shop.domain.book.exception.BookNotFoundException;
import shop.bookbom.shop.domain.bookauthor.entity.QBookAuthor;
import shop.bookbom.shop.domain.bookcategory.entity.QBookCategory;
import shop.bookbom.shop.domain.bookfile.entity.QBookFile;
import shop.bookbom.shop.domain.bookfiletype.entity.QBookFileType;
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

                .where(book.id.eq(bookId))
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
                .rightJoin(review.book, book)

                .leftJoin(book.authors, bookAuthor)
                .leftJoin(bookAuthor.author, author)

                .leftJoin(book.tags, bookTag)
                .leftJoin(bookTag.tag, tag)

                .leftJoin(book.bookFiles, bookFiles)
                .leftJoin(bookFiles.file, file)
                .leftJoin(bookFiles.bookFileType, fileType)

                .where(book.id.eq(bookId))
                .transform(groupBy(book.id).list(
                                Projections.constructor(BookMediumResponse.class,
                                        book.title,
                                        book.pubDate,
                                        book.cost,
                                        book.discountCost,
                                        Projections.constructor(PublisherSimpleInformation.class, book.publisher.name),
                                        Projections.constructor(PointRateSimpleInformation.class,
                                                book.pointRate.earnType.stringValue(),
                                                book.pointRate.earnPoint),
                                        list(Projections.constructor(AuthorDTO.class, author.id, bookAuthor.role, author.name)),
                                        list(Projections.constructor(TagDTO.class, tag.id, tag.name)),
                                        list(Projections.constructor(FileDTO.class, file.url, file.extension)),
                                        Projections.constructor(ReviewSimpleInformation.class, count(review),
                                                avg(review.rate)))
                        )
                );

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

                .where(book.id.eq(bookId))
                .transform(groupBy(book.id).list(
                        Projections.constructor(BookSimpleResponse.class,
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
    public Page<BookMediumResponse> getPageableBookMediumInfoById(Long bookId) {
        return null;
//        List<BookMediumResponse> result = from(book)
//                .join(book.pointRate)
//                .rightJoin(review)
//
//                .leftJoin(book.authors, bookAuthor)
//                .leftJoin(bookAuthor.author, author)
//
//                .leftJoin(book.tags, bookTag)
//                .leftJoin(bookTag.tag, tag)
//
//                .leftJoin(book.bookFiles, bookFiles)
//                .leftJoin(bookFiles.file, file)
//                .leftJoin(bookFiles.bookFileType, fileType)
//
//                .where(book.id.eq(bookId))
//                .transform(groupBy(book.id).list(
//                                Projections.constructor(BookMediumResponse.class,
//                                        book.title,
//                                        book.pubDate,
//                                        book.cost,
//                                        book.discountCost,
//                                        Projections.constructor(PublisherSimpleInformation.class, book.publisher.name),
//                                        Projections.constructor(PointRateSimpleInformation.class,
//                                                book.pointRate.earnType.stringValue(),
//                                                book.pointRate.earnPoint),
//                                        list(Projections.constructor(AuthorDTO.class, author.id, bookAuthor.role, author.name)),
//                                        list(Projections.constructor(TagDTO.class, tag.id, tag.name)),
//                                        list(Projections.constructor(FileDTO.class, file.url, file.extension)),
//                                        Projections.constructor(ReviewSimpleInformation.class, count(review),
//                                                avg(review.rate)))
//                        )
//                );
//
//        if (result.size() == 1) {// 항상 1
//            return result;
//        } else {// #todo 중복된 결과 조회 오류로 변경
//            throw new BookNotFoundException();
//        }
    }
}
