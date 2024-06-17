package shop.bookbom.shop.domain.pointrate.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.pointrate.entity.ApplyPointType;
import shop.bookbom.shop.domain.pointrate.entity.PointRate;

public interface PointRateRepository extends JpaRepository<PointRate, Long>, PointRateRepositoryCustom {
    Optional<PointRate> findByApplyType(ApplyPointType applyPointType);

    Optional<PointRate> findByName(String name);
}
