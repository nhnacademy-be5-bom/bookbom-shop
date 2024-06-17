package shop.bookbom.shop.domain.member.repository.impl;

import static shop.bookbom.shop.domain.member.entity.QMember.member;
import static shop.bookbom.shop.domain.membercoupon.entity.QMemberCoupon.memberCoupon;
import static shop.bookbom.shop.domain.order.entity.QOrder.order;
import static shop.bookbom.shop.domain.orderstatus.entity.QOrderStatus.orderStatus;
import static shop.bookbom.shop.domain.rank.entity.QRank.rank;
import static shop.bookbom.shop.domain.wish.entity.QWish.wish;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import shop.bookbom.shop.domain.member.dto.response.MemberInfoResponse;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.exception.MemberNotFoundException;
import shop.bookbom.shop.domain.member.repository.MemberRepositoryCustom;
import shop.bookbom.shop.domain.order.dto.response.OrderInfoResponse;
import shop.bookbom.shop.domain.order.entity.Order;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public MemberInfoResponse findMemberInfo(Long id) {
        Member memberResult = queryFactory
                .selectFrom(member)
                .join(member.rank, rank).fetchJoin()
                .where(member.id.eq(id))
                .fetchOne();

        if (memberResult == null) {
            throw new MemberNotFoundException();
        }

        Long couponCount = queryFactory
                .select(memberCoupon.count())
                .from(memberCoupon)
                .where(memberCoupon.member.eq(memberResult))
                .fetchOne();

        Long wishCount = queryFactory
                .select(wish.count())
                .from(wish)
                .where(wish.member.eq(memberResult))
                .fetchOne();

        List<Order> orders = queryFactory
                .selectFrom(order)
                .join(order.status, orderStatus).fetchJoin()
                .where(
                        order.user.eq(memberResult),
                        order.status.name.ne("결제전"))
                .orderBy(order.orderDate.desc())
                .limit(5)
                .fetch();

        List<OrderInfoResponse> lastOrders =
                orders.stream()
                        .map(OrderInfoResponse::of)
                        .collect(Collectors.toList());

        return MemberInfoResponse.builder()
                .id(memberResult.getId())
                .nickname(memberResult.getNickname())
                .rank(memberResult.getRank().getName())
                .lastOrders(lastOrders)
                .point(memberResult.getPoint())
                .couponCount((couponCount != null) ? couponCount : 0L)
                .wishCount((wishCount != null) ? wishCount : 0L)
                .build();
    }

    @Override
    public Member findMemberByIdFetchRank(Long id){
        return queryFactory
                .select(member)
                .from(member)
                .leftJoin(member.rank, rank).fetchJoin()
                .where(member.id.eq(id))
                .fetchOne();
    }
}
