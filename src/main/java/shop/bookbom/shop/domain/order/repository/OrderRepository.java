package shop.bookbom.shop.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.bookbom.shop.domain.order.entity.Order;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
}
