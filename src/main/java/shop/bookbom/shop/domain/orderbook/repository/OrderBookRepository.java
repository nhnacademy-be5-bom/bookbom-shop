package shop.bookbom.shop.domain.orderbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.bookbom.shop.domain.orderbook.entity.OrderBook;

@Repository
public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {
    
}
