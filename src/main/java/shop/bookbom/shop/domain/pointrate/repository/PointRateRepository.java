package shop.bookbom.shop.domain.pointrate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.pointrate.entity.PointRate;

public interface PointRateRepository extends JpaRepository<PointRate, Long> {
}
