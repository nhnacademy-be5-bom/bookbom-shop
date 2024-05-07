package shop.bookbom.shop.domain.order.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.order.entity.Order;


public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

    // 주문 번호로 주문 찾기
    Optional<Order> findByOrderNumber(String orderId);

}
