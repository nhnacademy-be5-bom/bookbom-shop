package shop.bookbom.shop.domain.membercoupon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.coupon.entity.Coupon;
import shop.bookbom.shop.domain.membercoupon.entity.MemberCoupon;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long>, MemberCouponRepositoryCustom {
    //Page<MemberCoupon> findByCoupon(Pageable pageable, Coupon coupon);
}
