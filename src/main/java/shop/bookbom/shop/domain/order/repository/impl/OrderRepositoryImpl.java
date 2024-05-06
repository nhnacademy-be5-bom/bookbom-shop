package shop.bookbom.shop.domain.order.repository.impl;

import static shop.bookbom.shop.domain.delivery.entity.QDelivery.delivery;
import static shop.bookbom.shop.domain.order.entity.QOrder.order;
import static shop.bookbom.shop.domain.orderstatus.entity.QOrderStatus.orderStatus;
import static shop.bookbom.shop.domain.payment.entity.QPayment.payment;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import shop.bookbom.shop.domain.order.dto.response.OrderManagementResponse;
import shop.bookbom.shop.domain.order.entity.Order;
import shop.bookbom.shop.domain.order.repository.OrderRepositoryCustom;
import shop.bookbom.shop.domain.orderstatus.entity.OrderStatus;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<OrderManagementResponse> getOrderManagement(Pageable pageable, LocalDate dateFrom, LocalDate dateTo,
                                                            String sort, OrderStatus status) {
        List<Order> result = queryFactory.select(order)
                .from(order)
                .leftJoin(order.status, orderStatus).fetchJoin()
                .leftJoin(order.payment, payment).fetchJoin()
                .leftJoin(order.delivery, delivery).fetchJoin()
                .where(
                        orderDateMin(dateFrom),
                        orderDateMax(dateTo),
                        inOrderStatus(status)
                )
                .orderBy(orderSortBy(sort))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<OrderManagementResponse> content = result.stream()
                .map(OrderManagementResponse::of)
                .collect(Collectors.toList());

        JPAQuery<Long> countQuery = queryFactory.select(order.count())
                .from(order)
                .leftJoin(order.status, orderStatus)
                .leftJoin(order.payment, payment)
                .leftJoin(order.delivery, delivery)
                .where(
                        orderDateMin(dateFrom),
                        orderDateMax(dateTo),
                        inOrderStatus(status)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private OrderSpecifier<?> orderSortBy(String sortCond) {
        if (sortCond.isEmpty()) {
            return order.orderDate.desc();
        }
        switch (sortCond) {
            case "old":
                return order.orderDate.asc();
            case "expected_delivery":
                return order.delivery.estimatedDate.asc();
            case "recent":
            default:
                return order.orderDate.desc();
        }
    }

    private BooleanExpression inOrderStatus(OrderStatus status) {
        return status == null ? null : order.status.eq(status);
    }

    private BooleanExpression orderDateMax(LocalDate orderDateMax) {
        return orderDateMax == null ? null : order.orderDate.lt(orderDateMax.plusDays(1).atStartOfDay());
    }

    private BooleanExpression orderDateMin(LocalDate orderDateMin) {
        return orderDateMin == null ? null : order.orderDate.goe(orderDateMin.atStartOfDay());
    }
}
