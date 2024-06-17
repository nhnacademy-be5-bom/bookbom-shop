package shop.bookbom.shop.domain.pointrate.repository;

import java.util.List;
import shop.bookbom.shop.domain.pointrate.repository.dto.PointRateResponse;

public interface PointRateRepositoryCustom {
    List<PointRateResponse> getPointPolicies();
}
