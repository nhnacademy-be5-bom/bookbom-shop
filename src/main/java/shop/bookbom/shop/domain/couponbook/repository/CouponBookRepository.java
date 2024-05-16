package shop.bookbom.shop.domain.couponbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.couponbook.entity.CouponBook;

public interface CouponBookRepository extends JpaRepository<CouponBook, Long> {
}
