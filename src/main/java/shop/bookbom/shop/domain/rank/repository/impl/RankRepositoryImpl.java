package shop.bookbom.shop.domain.rank.repository.impl;

import static shop.bookbom.shop.domain.rank.entity.QRank.rank;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
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
                .leftJoin(rank.pointRate).fetchJoin()
                .where(rank.name.eq(name))
                .fetchOne();
        if (result == null) {
            throw new RankNotFoundException();
        }
        return result;
    }
}
