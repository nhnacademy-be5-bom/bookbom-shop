package shop.bookbom.shop.domain.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
