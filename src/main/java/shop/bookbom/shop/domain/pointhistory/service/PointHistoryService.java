package shop.bookbom.shop.domain.pointhistory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.bookbom.shop.domain.pointhistory.dto.response.PointHistoryResponse;
import shop.bookbom.shop.domain.pointhistory.entity.ChangeReason;

public interface PointHistoryService {
    Page<PointHistoryResponse> findPointHistory(Long memberId, Pageable pageable, ChangeReason changeReason);
}
