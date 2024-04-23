package shop.bookbom.shop.domain.pointhistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.pointhistory.entity.PointHistory;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

}
