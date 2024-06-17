package shop.bookbom.shop.domain.review.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
    Optional<Long> countByBookId(Long bookId);

    @Query("select avg(r.rate) from Review r where r.book.id = :bookId")
    Optional<Double> avgRateByBookId(@Param("bookId") Long bookId);

    boolean existsByMemberAndBook(Member member, Book book);
}
