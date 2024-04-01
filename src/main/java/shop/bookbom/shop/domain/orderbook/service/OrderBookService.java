package shop.bookbom.shop.domain.orderbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.bookbom.shop.domain.orderbook.entity.OrderBook;
import shop.bookbom.shop.domain.orderbook.repository.OrderBookRepository;

@Service
@RequiredArgsConstructor
public class OrderBookService {
    private final OrderBookRepository orderBookRepository;

    public void saveOrderBook(OrderBook orderBook) {
        orderBookRepository.save(orderBook);
    }
}
