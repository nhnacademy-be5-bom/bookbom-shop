package shop.bookbom.shop.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
