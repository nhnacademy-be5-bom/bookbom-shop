package shop.bookbom.shop.domain.pointrate.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.pointrate.repository.PointRateRepository;
import shop.bookbom.shop.domain.pointrate.repository.dto.PointRateResponse;

@Service
@RequiredArgsConstructor
public class PointRateService {
    private final PointRateRepository pointRateRepository;

    @Transactional(readOnly = true)
    public List<PointRateResponse> getPointPolicies() {
        return pointRateRepository.getPointPolicies();
    }
}
