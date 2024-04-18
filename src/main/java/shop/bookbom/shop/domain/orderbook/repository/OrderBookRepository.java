package shop.bookbom.shop.domain.orderbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.orderbook.entity.OrderBook;

public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {

}
