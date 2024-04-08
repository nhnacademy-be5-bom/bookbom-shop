package shop.bookbom.shop.domain.booktag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.booktag.entity.BookTag;

public interface BooktagRepository extends JpaRepository<BookTag, Long> {
}
