package shop.bookbom.shop.domain.orderbook.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.order.entity.Order;
import shop.bookbom.shop.domain.orderbook.entity.OrderBook;

public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {

    List<OrderBook> findByOrder(Order order);

}
