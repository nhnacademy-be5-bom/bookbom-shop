package shop.bookbom.shop.domain.book.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.order.dto.response.BookTitleAndCostResponse;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT new shop.bookbom.shop.domain.order.dto.response.BookTitleAndCostResponse(b.title, b.cost) FROM Book b WHERE b.id = :bookId")
    Optional<BookTitleAndCostResponse> getTitleAndCostById(@Param("bookId") Long bookId);
}
