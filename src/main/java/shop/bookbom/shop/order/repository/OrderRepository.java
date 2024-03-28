package shop.bookbom.shop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.bookbom.shop.order.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Integer countById(Long id);
}
