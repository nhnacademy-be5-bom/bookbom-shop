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

    /**
     * 포인트 정책을 수정하는 메서드입니다.
     *
     * @param id        수정할 포인트 정책의 id
     * @param earnType  수정할 적립 유형
     * @param earnPoint 수정할 적립율
     * @return 수정된 포인트 정책
     */
    PointRateResponse updatePolicy(Long id, String earnType, int earnPoint);
}
