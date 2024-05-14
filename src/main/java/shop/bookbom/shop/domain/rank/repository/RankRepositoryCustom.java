package shop.bookbom.shop.domain.rank.repository;

import java.util.List;
import shop.bookbom.shop.domain.rank.entity.Rank;

public interface RankRepositoryCustom {
    /**
     * 이름으로 Rank를 조회하고 PointRate를 fetch join하여 반환하는 메서드입니다.
     *
     * @param name Rank 이름
     * @return Rank 엔티티
     */
    Rank getRankByNameFetchPointRate(String name);

    /**
     * 모든 Rank를 조회하고 PointRate를 fetch join하여 반환하는 메서드입니다.
     *
     * @return Rank 엔티티 리스트
     */
    List<Rank> getAllRankFetchPointRate();
}
