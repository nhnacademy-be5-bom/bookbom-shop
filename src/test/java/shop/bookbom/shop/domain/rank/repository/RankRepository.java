package shop.bookbom.shop.domain.rank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.rank.entity.Rank;

public interface RankRepository extends JpaRepository<Rank, Long> {
}
