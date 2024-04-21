package shop.bookbom.shop.domain.pointrate.service;

import java.util.List;
import shop.bookbom.shop.domain.pointrate.repository.dto.PointRateResponse;

public interface PointRateService {
    /**
     * 포인트 정책 목록을 조회하는 메서드입니다.
     *
     * @return 포인트 정책 목록
     */
    List<PointRateResponse> getPointPolicies();
}
