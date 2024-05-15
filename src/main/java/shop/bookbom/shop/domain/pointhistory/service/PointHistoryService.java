package shop.bookbom.shop.domain.pointhistory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.pointhistory.dto.response.PointHistoryResponse;
import shop.bookbom.shop.domain.pointhistory.entity.ChangeReason;

public interface PointHistoryService {
    Page<PointHistoryResponse> findPointHistory(Long memberId, Pageable pageable, ChangeReason changeReason);

    void decreasePoint(Member member, int usedPoint);

    void earnPointByBook(Member member, Integer totalOrderCost);

    void earnPointByRank(Member member, Integer totalOrderCost);
}
