package shop.bookbom.shop.domain.bookfile.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.bookbom.shop.domain.bookfile.entity.BookFile;
import shop.bookbom.shop.domain.bookfile.entity.QBookFile;
import shop.bookbom.shop.domain.bookfiletype.entity.QBookFileType;
import shop.bookbom.shop.domain.file.entity.QFile;

public class BookFileRepositoryCustomImpl extends QuerydslRepositorySupport implements BookFileRepositoryCustom {
    
    public BookFileRepositoryCustomImpl() {
        super(BookFile.class);
    }

    @Override
    public String getBookImageUrl(Long bookId) {
        QBookFile bookFile = QBookFile.bookFile;
        QFile file = QFile.file;
        QBookFileType bookFileType = QBookFileType.bookFileType;

        return from(bookFile)
                .join(bookFile.file, file)
                .join(bookFile.bookFileType, bookFileType)
                .where(bookFile.book.id.eq(bookId)
                        .and(bookFileType.name.eq("img")))
                .select(file.url)
                .fetchFirst();
    }
}
