package shop.bookbom.shop.domain.coupon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.bookbom.shop.domain.coupon.dto.response.MyCouponRecordResponse;
import shop.bookbom.shop.domain.coupon.entity.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponCustomRepository {
}
