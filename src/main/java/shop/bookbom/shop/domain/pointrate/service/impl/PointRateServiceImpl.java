package shop.bookbom.shop.domain.pointrate.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.pointrate.entity.EarnPointType;
import shop.bookbom.shop.domain.pointrate.entity.PointRate;
import shop.bookbom.shop.domain.pointrate.exception.PointRateNotFoundException;
import shop.bookbom.shop.domain.pointrate.repository.PointRateRepository;
import shop.bookbom.shop.domain.pointrate.repository.dto.PointRateResponse;
import shop.bookbom.shop.domain.pointrate.service.PointRateService;

@Service
@RequiredArgsConstructor
public class PointRateServiceImpl implements PointRateService {
    private final PointRateRepository pointRateRepository;

    @Transactional(readOnly = true)
    public List<PointRateResponse> getPointPolicies() {
        return pointRateRepository.getPointPolicies();
    }

    @Transactional
    public PointRateResponse updatePolicy(Long id, String earnType, int earnPoint) {
        PointRate pointRate = pointRateRepository.findById(id)
                .orElseThrow(PointRateNotFoundException::new);
        EarnPointType earnPointType = EarnPointType.valueOf(earnType);
        pointRate.updatePolicy(earnPointType, earnPoint);
        return PointRateResponse.builder()
                .id(pointRate.getId())
                .name(pointRate.getName())
                .earnType(pointRate.getEarnType())
                .earnPoint(pointRate.getEarnPoint())
                .build();
    }
}
