package shop.bookbom.shop.domain.file.repository.custom;

import static shop.bookbom.shop.domain.book.entity.QBook.book;
import static shop.bookbom.shop.domain.bookfile.entity.QBookFile.bookFile;
import static shop.bookbom.shop.domain.bookfiletype.entity.QBookFileType.bookFileType;
import static shop.bookbom.shop.domain.file.entity.QFile.file;

import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import shop.bookbom.shop.domain.file.entity.File;

/**
 * packageName    : shop.bookbom.shop.domain.file.repository.custom
 * fileName       : FileRepositoryCustom
 * author         : UuLaptop
 * date           : 2024-04-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-22        UuLaptop       최초 생성
 */
@Repository
public class FileRepositoryImpl extends QuerydslRepositorySupport implements FileRepositoryCustom {
    public FileRepositoryImpl() {
        super(File.class);
    }

    @Override
    public Optional<File> findThumbnailByBookId(Long bookId) {
        File thumbNail = from(book).rightJoin(bookFile).on(book.id.eq(bookFile.book.id))
                .rightJoin(bookFileType).on(bookFile.bookFileType.id.eq(bookFileType.id))
                .rightJoin(file).on(bookFile.file.id.eq(file.id))
                .where(book.id.eq(bookId).and(bookFileType.id.eq(1L)))
                .select(bookFile.file)
                .fetchOne();

        return Optional.of(thumbNail);
    }
}
