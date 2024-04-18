package shop.bookbom.shop.domain.booktag.repository;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.booktag.entity.BookTag;

public interface BookTagRepository extends JpaRepository<BookTag, Long> {
    // 책 id를 통해 BookTag의 리스트를 반환
    List<BookTag> findAllByBookId(Long bookId);

    // bookId와 tagId를 통해 BookTag의 리스트를 반환
    Optional<BookTag> findByBookIdAndTagId(long bookId, long tagId);
}
