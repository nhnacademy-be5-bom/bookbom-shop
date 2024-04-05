package shop.bookbom.shop.domain.book.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    /**
     * bookId로 Book찾는 메소드
     *
     * @param bookId
     * @return Optional<Book>
     */
    Optional<Book> findById(Long bookId);
}
