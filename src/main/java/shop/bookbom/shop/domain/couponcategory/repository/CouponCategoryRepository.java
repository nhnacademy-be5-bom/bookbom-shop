package shop.bookbom.shop.domain.couponcategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.couponcategory.entity.CouponCategory;

public interface CouponCategoryRepository extends JpaRepository<CouponCategory, Long> {
}
