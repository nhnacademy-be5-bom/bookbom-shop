package shop.bookbom.shop.domain.membercoupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.membercoupon.entity.MemberCoupon;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long>, MemberCouponRepositoryCustom {
//    Optional<MemberCoupon> findByCoupon(Coupon coupon);


}
