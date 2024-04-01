package shop.bookbom.shop.domain.bookfile.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.bookfiletype.entity.BookFileType;
import shop.bookbom.shop.domain.file.entity.File;

@Entity
@Table(name = "book_file")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_file_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_file_type_id", nullable = false)
    private BookFileType bookFileType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    @Builder
    public BookFile(Book book, BookFileType bookFileType, File file) {
        this.book = book;
        this.bookFileType = bookFileType;
        this.file = file;
    }
}
