package shop.bookbom.shop.domain.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.coupon.entity.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
