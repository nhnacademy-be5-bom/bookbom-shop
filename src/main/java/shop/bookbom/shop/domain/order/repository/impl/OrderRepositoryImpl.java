package shop.bookbom.shop.domain.order.repository.impl;

import static shop.bookbom.shop.domain.book.entity.QBook.book;
import static shop.bookbom.shop.domain.bookfile.entity.QBookFile.bookFile;
import static shop.bookbom.shop.domain.bookfiletype.entity.QBookFileType.bookFileType;
import static shop.bookbom.shop.domain.delivery.entity.QDelivery.delivery;
import static shop.bookbom.shop.domain.deliveryaddress.entity.QDeliveryAddress.deliveryAddress1;
import static shop.bookbom.shop.domain.file.entity.QFile.file;
import static shop.bookbom.shop.domain.order.entity.QOrder.order;
import static shop.bookbom.shop.domain.orderbook.entity.QOrderBook.orderBook;
import static shop.bookbom.shop.domain.payment.entity.QPayment.payment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import shop.bookbom.shop.domain.order.dto.response.OrderBookResponse;
import shop.bookbom.shop.domain.order.dto.response.OrderDetailResponse;
import shop.bookbom.shop.domain.order.entity.Order;
import shop.bookbom.shop.domain.order.exception.OrderNotFoundException;
import shop.bookbom.shop.domain.order.repository.OrderRepositoryCustom;
import shop.bookbom.shop.domain.orderbook.entity.OrderBook;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public OrderDetailResponse getOrderById(Long id) {
        Order orderResult = queryFactory.select(order)
                .from(order)
                .join(order.payment, payment).fetchJoin()
                .join(order.delivery, delivery).fetchJoin()
                .join(delivery.deliveryAddress, deliveryAddress1).fetchJoin()
                .where(order.id.eq(id))
                .fetchOne();

        if (orderResult == null) {
            throw new OrderNotFoundException();
        }

        List<OrderBook> orderBookResults = queryFactory.select(orderBook)
                .from(orderBook)
                .join(orderBook.book, book).fetchJoin()
                .join(book.bookFiles, bookFile).fetchJoin()
                .join(bookFile.bookFileType, bookFileType).fetchJoin()
                .join(bookFile.file, file).fetchJoin()
                .where(orderBook.order.eq(orderResult))
                .fetch();

        List<OrderBookResponse> orderBooks = orderBookResults.stream()
                .map(OrderBookResponse::of)
                .collect(Collectors.toList());


        return OrderDetailResponse.of(orderResult, orderBooks);
    }
}
