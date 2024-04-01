package shop.bookbom.shop.booktag.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.booktag.entity.BookTag;

public interface BookTagRepository extends JpaRepository<BookTag, Long> {
    // 책 id를 통해 BookTag의 리스트를 반환
    List<BookTag> findAllByBookId(Long bookId);
}
