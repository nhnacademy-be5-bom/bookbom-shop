package shop.bookbom.shop.domain.pointhistory.service.impl;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.exception.MemberNotFoundException;
import shop.bookbom.shop.domain.member.repository.MemberRepository;
import shop.bookbom.shop.domain.pointhistory.dto.response.PointHistoryResponse;
import shop.bookbom.shop.domain.pointhistory.entity.ChangeReason;
import shop.bookbom.shop.domain.pointhistory.entity.PointHistory;
import shop.bookbom.shop.domain.pointhistory.entity.PointHistoryDetail;
import shop.bookbom.shop.domain.pointhistory.repository.PointHistoryRepository;
import shop.bookbom.shop.domain.pointhistory.service.PointHistoryService;
import shop.bookbom.shop.domain.pointrate.entity.ApplyPointType;
import shop.bookbom.shop.domain.pointrate.entity.EarnPointType;
import shop.bookbom.shop.domain.pointrate.entity.PointRate;
import shop.bookbom.shop.domain.pointrate.exception.PointRateNotFoundException;
import shop.bookbom.shop.domain.pointrate.repository.PointRateRepository;

@Service
@RequiredArgsConstructor
public class PointHistoryServiceImpl implements PointHistoryService {
    private final MemberRepository memberRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final PointRateRepository pointRateRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<PointHistoryResponse> findPointHistory(Long memberId, Pageable pageable, ChangeReason changeReason) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        return pointHistoryRepository.getPointHistory(member, pageable, changeReason);
    }

    /**
     * 포인트 감소 메소드
     *
     * @param member
     * @param usedPoint
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void decreasePoint(Member member, int usedPoint) {

        //포인트 히스토리 추가
        PointHistory pointHistory = PointHistory.builder().changePoint(-usedPoint)
                .changeReason(ChangeReason.USE)
                .member(member)
                .detail(PointHistoryDetail.PAYMENT_USE)
                .changeDate(LocalDateTime.now())
                .build();
        pointHistoryRepository.save(pointHistory);
        //회원 포인트 차감
        member.updatePoints(member.getPoint() - usedPoint);
        memberRepository.save(member);

    }

    /**
     * 회원 등급에 따른 포인트 적립
     *
     * @param member
     * @param totalOrderCost
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void earnPointByRank(Member member, Integer totalOrderCost) {
        PointRate pointRate = pointRateRepository.findById(member.getRank().getPointRate().getId())
                .orElseThrow(PointRateNotFoundException::new);
        int changePoint = totalOrderCost * pointRate.getEarnPoint() / 100;
        PointHistory pointHistory = PointHistory.builder().changePoint(changePoint)
                .changeReason(ChangeReason.EARN)
                .member(member)
                .detail(PointHistoryDetail.ORDER_EARN)
                .changeDate(LocalDateTime.now())
                .build();
        pointHistoryRepository.save(pointHistory);
    }

    /**
     * 주문 책에 따른 포인트 적립
     *
     * @param member
     * @param totalOrderCost
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void earnPointByBook(Member member, Integer totalOrderCost) {
        PointRate pointRate = pointRateRepository.findByApplyType(ApplyPointType.BOOK)
                .orElseThrow(PointRateNotFoundException::new);
        PointHistory pointHistory = null;
        if (pointRate.getEarnType() == EarnPointType.COST) {
            pointHistory = PointHistory.builder().changePoint(pointRate.getEarnPoint())
                    .changeDate(LocalDateTime.now())
                    .detail(PointHistoryDetail.ORDER_EARN)
                    .member(member)
                    .changeReason(ChangeReason.EARN)
                    .build();
        } else if (pointRate.getEarnType() == EarnPointType.RATE) {
            int changePoint = totalOrderCost * pointRate.getEarnPoint() / 100;
            pointHistory = PointHistory.builder().changePoint(changePoint)
                    .changeDate(LocalDateTime.now())
                    .detail(PointHistoryDetail.ORDER_EARN)
                    .member(member)
                    .changeReason(ChangeReason.EARN)
                    .build();
        }

        pointHistoryRepository.save(pointHistory);

    }
}
