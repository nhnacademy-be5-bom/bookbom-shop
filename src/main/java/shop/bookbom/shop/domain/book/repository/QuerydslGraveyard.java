package shop.bookbom.shop.domain.book.repository;

/**
 * packageName    : shop.bookbom.shop.domain.bookfile.repository
 * fileName       : QuerydslGraveyard
 * author         : UuLaptop
 * date           : 2024-04-16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-16        UuLaptop       최초 생성
 */
public class QuerydslGraveyard {
    /*
            .leftJoin(bookAuthor).on(book.id.eq(bookAuthor.book.id))
                .leftJoin(author).on(bookAuthor.author.id.eq(author.id))

                .leftJoin(bookTag).on(book.id.eq(bookTag.book.id))
                .leftJoin(tag).on(bookTag.tag.id.eq(tag.id))

                .leftJoin(bookCategory).on(book.id.eq(bookCategory.book.id))
                .leftJoin(category).on(bookCategory.category.id.eq(category.id))

                .leftJoin(bookFiles).on(book.id.eq(bookFiles.book.id))
                .leftJoin(file).on(bookFiles.file.id.eq(file.id))
                .leftJoin(fileType).on(bookFiles.bookFileType.id.eq(fileType.id))

                .leftJoin(review).on(book.id.eq(review.book.id))
     */
}
