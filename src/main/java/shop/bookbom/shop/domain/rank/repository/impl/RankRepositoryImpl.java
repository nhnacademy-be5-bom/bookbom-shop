package shop.bookbom.shop.domain.rank.repository.impl;

import static shop.bookbom.shop.domain.pointrate.entity.QPointRate.pointRate;
import static shop.bookbom.shop.domain.rank.entity.QRank.rank;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import shop.bookbom.shop.domain.pointrate.entity.ApplyPointType;
import shop.bookbom.shop.domain.rank.dto.response.RankResponse;
import shop.bookbom.shop.domain.rank.entity.Rank;
import shop.bookbom.shop.domain.rank.exception.RankNotFoundException;
import shop.bookbom.shop.domain.rank.repository.RankRepositoryCustom;

@RequiredArgsConstructor
public class RankRepositoryImpl implements RankRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Rank getRankByNameFetchPointRate(String name) {
        Rank result = queryFactory
                .select(rank)
                .from(rank)
                .leftJoin(rank.pointRate, pointRate).fetchJoin()
                .where(rank.name.eq(name))
                .fetchOne();
        if (result == null) {
            throw new RankNotFoundException();
        }
        return result;
    }

    @Override
    public List<RankResponse> getAllRankFetchPointRate(){
        List<Tuple> result = queryFactory
                .select(rank.name,
                        pointRate.earnPoint,
                        pointRate.earnType)
                .from(rank)
                .leftJoin(rank.pointRate, pointRate)
                .where(pointRate.applyType.eq(ApplyPointType.RANK))
                .fetch();
        List<RankResponse> rankResponses = new ArrayList<>();
        for (Tuple tuple : result) {
            rankResponses.add(RankResponse.builder()
                    .name(tuple.get(rank.name))
                    .earnPoint(tuple.get(pointRate.earnPoint))
                    .earnType(tuple.get(pointRate.earnType))
                    .build());
        }

        return rankResponses;
    }
}
