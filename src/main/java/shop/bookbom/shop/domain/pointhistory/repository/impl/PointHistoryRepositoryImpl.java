package shop.bookbom.shop.domain.pointhistory.repository.impl;

import static shop.bookbom.shop.domain.member.entity.QMember.member;
import static shop.bookbom.shop.domain.pointhistory.entity.QPointHistory.pointHistory;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.pointhistory.dto.response.PointHistoryResponse;
import shop.bookbom.shop.domain.pointhistory.dto.response.QPointHistoryResponse;
import shop.bookbom.shop.domain.pointhistory.entity.ChangeReason;
import shop.bookbom.shop.domain.pointhistory.repository.PointHistoryRepositoryCustom;

@RequiredArgsConstructor
public class PointHistoryRepositoryImpl implements PointHistoryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PointHistoryResponse> getPointHistory(Member mem, Pageable pageable, ChangeReason changeReason) {
        List<PointHistoryResponse> content = queryFactory
                .select(new QPointHistoryResponse(
                        pointHistory.id,
                        pointHistory.changeReason.stringValue(),
                        pointHistory.detail.stringValue(),
                        pointHistory.changePoint,
                        pointHistory.changeDate
                ))
                .from(pointHistory)
                .leftJoin(pointHistory.member, member)
                .where(
                        pointHistory.member.eq(mem),
                        changeReasonEq(changeReason)
                )
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(pointHistory.count())
                .from(pointHistory)
                .leftJoin(pointHistory.member, member)
                .where(
                        pointHistory.member.eq(mem),
                        changeReasonEq(changeReason)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression changeReasonEq(ChangeReason changeReason) {
        return changeReason != null ? pointHistory.changeReason.eq(ChangeReason.valueOf(changeReason.name())) : null;
    }
}
