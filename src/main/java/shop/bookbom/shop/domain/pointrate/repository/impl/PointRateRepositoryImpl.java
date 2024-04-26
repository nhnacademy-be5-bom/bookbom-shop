package shop.bookbom.shop.domain.pointrate.repository.impl;

import static shop.bookbom.shop.domain.pointrate.entity.QPointRate.pointRate;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import shop.bookbom.shop.domain.pointrate.repository.PointRateRepositoryCustom;
import shop.bookbom.shop.domain.pointrate.repository.dto.PointRateResponse;
import shop.bookbom.shop.domain.pointrate.repository.dto.QPointRateResponse;

@RequiredArgsConstructor
public class PointRateRepositoryImpl implements PointRateRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<PointRateResponse> getPointPolicies() {
        return queryFactory.select(new QPointRateResponse(
                        pointRate.id,
                        pointRate.name,
                        pointRate.earnType,
                        pointRate.earnPoint))
                .from(pointRate)
                .fetch();
    }
}
