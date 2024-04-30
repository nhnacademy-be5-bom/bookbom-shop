package shop.bookbom.shop.domain.rank.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.rank.entity.Rank;

public interface RankRepository extends JpaRepository<Rank, Long> {
    Optional<Rank> findByName(String name);
}
