package shop.bookbom.shop.domain.pointhistory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.pointhistory.dto.response.PointHistoryResponse;
import shop.bookbom.shop.domain.pointhistory.entity.ChangeReason;

public interface PointHistoryRepositoryCustom {
    /**
     * 회원의 포인트 적립/사용 내역을 조회하는 메서드입니다.
     *
     * @param member       회원 정보
     * @param pageable     페이지 정보
     * @param changeReason 변동사유 필터링 (ex. 적립 / 사용)
     * @return 포인트 적립/사용 내역
     */
    Page<PointHistoryResponse> getPointHistory(Member member, Pageable pageable, ChangeReason changeReason);
}
