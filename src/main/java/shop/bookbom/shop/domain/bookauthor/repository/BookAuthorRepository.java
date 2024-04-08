package shop.bookbom.shop.domain.bookauthor.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.bookauthor.entity.BookAuthor;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {
    List<BookAuthor> findAllByBook(Long bookId);
}
