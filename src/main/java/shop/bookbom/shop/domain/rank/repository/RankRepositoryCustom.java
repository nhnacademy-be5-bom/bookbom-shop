package shop.bookbom.shop.domain.rank.repository;

import shop.bookbom.shop.domain.rank.entity.Rank;

public interface RankRepositoryCustom {
    Rank getRankByNameFetchPointRate(String name);
}
