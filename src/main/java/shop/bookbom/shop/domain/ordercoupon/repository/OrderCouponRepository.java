package shop.bookbom.shop.domain.ordercoupon.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.order.entity.Order;
import shop.bookbom.shop.domain.ordercoupon.entity.OrderCoupon;

public interface OrderCouponRepository extends JpaRepository<OrderCoupon, Long> {
    Optional<OrderCoupon> findByOrder(Order order);

    boolean existsByOrder(Order order);

}
