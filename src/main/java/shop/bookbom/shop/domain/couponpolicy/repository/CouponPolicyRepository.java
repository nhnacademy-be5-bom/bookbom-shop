package shop.bookbom.shop.domain.couponpolicy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.couponpolicy.dto.CouponPolicyInfoDto;
import shop.bookbom.shop.domain.couponpolicy.entity.CouponPolicy;

public interface CouponPolicyRepository extends JpaRepository<CouponPolicy, Long>, CouponPolicyCustomRepository {
}
