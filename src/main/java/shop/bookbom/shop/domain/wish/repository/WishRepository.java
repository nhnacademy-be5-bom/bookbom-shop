package shop.bookbom.shop.domain.wish.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.wish.entity.Wish;

public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> getBookIdByMemberId(Long userId);
    Long countByMemberId(Long userId);
    Wish findByBookIdAndMemberId(Long bookId, Long userId);
    boolean existsByBookIdAndMemberId(Long bookId, Long userId);
}
