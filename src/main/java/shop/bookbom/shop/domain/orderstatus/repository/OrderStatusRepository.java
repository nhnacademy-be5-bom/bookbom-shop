package shop.bookbom.shop.domain.orderstatus.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.orderstatus.entity.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    Optional<OrderStatus> findByName(String name);
}
