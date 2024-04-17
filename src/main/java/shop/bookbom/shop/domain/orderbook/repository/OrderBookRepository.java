package shop.bookbom.shop.domain.orderbook.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.orderbook.entity.OrderBook;

public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {
    Optional<List<OrderBook>> findByOrder_Id(Long orderId);

}
