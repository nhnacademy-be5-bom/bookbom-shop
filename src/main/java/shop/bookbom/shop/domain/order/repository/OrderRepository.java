package shop.bookbom.shop.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.order.entity.Order;


public interface OrderRepository extends JpaRepository<Order, Long> {

}
