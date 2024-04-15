package shop.bookbom.shop.domain.book.repository.custom;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.querydsl.jpa.JPAExpressions.selectFrom;

import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.bookbom.shop.domain.author.entity.QAuthor;
import shop.bookbom.shop.domain.book.dto.response.BookDetailResponse;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.entity.QBook;
import shop.bookbom.shop.domain.bookauthor.entity.QBookAuthor;
import shop.bookbom.shop.domain.bookcategory.entity.QBookCategory;
import shop.bookbom.shop.domain.bookfile.entity.QBookFile;
import shop.bookbom.shop.domain.bookfiletype.entity.QBookFileType;
import shop.bookbom.shop.domain.booktag.entity.QBookTag;
import shop.bookbom.shop.domain.category.entity.QCategory;
import shop.bookbom.shop.domain.file.entity.QFile;
import shop.bookbom.shop.domain.pointrate.entity.QPointRate;
import shop.bookbom.shop.domain.publisher.entity.QPublisher;
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
    public BookRepositoryImpl() {
        super(Book.class);
    }

    @Override
    public List<BookDetailResponse> getBookDetailById(Long bookId) {
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

//        PublisherSimpleInformation publisherDTO =
//                PublisherSimpleInformation.builder()
//                        .publisher(select(publisher).from(publisher).innerJoin(book).where(book.id.eq(bookId))
//                                .fetchOne())
//                        .build();
//
//        PointRateSimpleInformation pointRateDTO =
//                PointRateSimpleInformation.builder()
//                        .pointRate(select(pointRate).from(pointRate).innerJoin(book).where(book.id.eq(bookId))
//                                .fetchOne())
//                        .build();
//
//        List<AuthorDTO> authors = select(new QAuthorDTO(author.id, bookAuthor.role, author.name))
//                .from(author)
//                .innerJoin(bookAuthor).on(book.id.eq(bookAuthor.book.id))
//                .innerJoin(author).on(author.id.eq(bookAuthor.author.id))
//                .where(book.id.eq(bookId))
//                .fetch();
//
//        List<TagDTO> tags = select(new QTagDTO(tag.id, tag.name))
//                .from(tag)
//                .innerJoin(bookTag).on(book.id.eq(bookTag.book.id))
//                .innerJoin(tag).on(tag.id.eq(bookTag.tag.id))
//                .where(book.id.eq(bookId))
//                .fetch();
//
//        List<CategoryDTO> categories = select(new QCategoryDTO(category))
//                .from(category)
//                .innerJoin(bookCategory).on(book.id.eq(bookCategory.book.id))
//                .innerJoin(category).on(category.id.eq(bookCategory.category.id))
//                .where(book.id.eq(bookId))
//                .fetch();


        //                .innerJoin(bookFiles).on(book.id.eq(bookFiles.book.id))
//                .innerJoin(file).on(file.id.eq(bookFiles.file.id))
//                .innerJoin(fileType).on(fileType.id.eq(bookFiles.bookFileType.id))


        return selectFrom(book)
                .leftJoin(author).on(book.id.eq(bookAuthor.author.id))
                .leftJoin(tag).on(book.id.eq(bookTag.tag.id))
                .leftJoin(category).on(book.id.eq(bookCategory.category.id))
                .where(book.id.eq(bookId))
                .distinct()
                .transform(
                        groupBy(book.id)
                                .list(Projections.constructor(BookDetailResponse.class,
                                        book,
                                        list(bookAuthor),
                                        list(tag),
                                        list(category)
                                )));
    }
}
