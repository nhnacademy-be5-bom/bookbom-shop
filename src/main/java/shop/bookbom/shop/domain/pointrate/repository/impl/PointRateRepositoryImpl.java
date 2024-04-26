package shop.bookbom.shop.domain.pointrate.repository.impl;

import static shop.bookbom.shop.domain.pointrate.entity.QPointRate.pointRate;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import shop.bookbom.shop.domain.pointrate.repository.PointRateRepositoryCustom;
import shop.bookbom.shop.domain.pointrate.repository.dto.PointRateResponse;
import shop.bookbom.shop.domain.pointrate.repository.dto.QPointRateResponse;

public class PointRateRepositoryImpl implements PointRateRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public PointRateRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

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
