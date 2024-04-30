package shop.bookbom.shop.domain.users.repository.impl;

import static shop.bookbom.shop.domain.order.entity.QOrder.order;
import static shop.bookbom.shop.domain.orderstatus.entity.QOrderStatus.orderStatus;

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
import shop.bookbom.shop.domain.order.dto.response.OrderInfoResponse;
import shop.bookbom.shop.domain.order.entity.Order;
import shop.bookbom.shop.domain.users.dto.OrderDateCondition;
import shop.bookbom.shop.domain.users.entity.User;
import shop.bookbom.shop.domain.users.repository.UserRepositoryCustom;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<OrderInfoResponse> getOrders(User user, Pageable pageable, OrderDateCondition condition) {
        List<Order> result = queryFactory.selectFrom(order)
                .leftJoin(order.status, orderStatus).fetchJoin()
                .where(
                        order.user.eq(user),
                        orderDateMin(condition.getOrderDateMin()),
                        orderDateMax(condition.getOrderDateMax())
                )
                .orderBy(order.orderDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<OrderInfoResponse> content = result.stream()
                .map(OrderInfoResponse::of)
                .collect(Collectors.toList());


        JPAQuery<Long> countQuery = queryFactory.select(order.count())
                .from(order)
                .where(
                        order.user.eq(user),
                        orderDateMin(condition.getOrderDateMin()),
                        orderDateMax(condition.getOrderDateMax())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression orderDateMax(LocalDate orderDateMax) {
        return orderDateMax == null ? null : order.orderDate.lt(orderDateMax.plusDays(1).atStartOfDay());
    }

    private BooleanExpression orderDateMin(LocalDate orderDateMin) {
        return orderDateMin == null ? null : order.orderDate.goe(orderDateMin.atStartOfDay());
    }
}
