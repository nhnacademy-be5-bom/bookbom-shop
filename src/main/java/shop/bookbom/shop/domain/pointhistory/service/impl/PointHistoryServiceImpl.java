package shop.bookbom.shop.domain.pointhistory.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.exception.MemberNotFoundException;
import shop.bookbom.shop.domain.member.repository.MemberRepository;
import shop.bookbom.shop.domain.pointhistory.dto.response.PointHistoryResponse;
import shop.bookbom.shop.domain.pointhistory.entity.ChangeReason;
import shop.bookbom.shop.domain.pointhistory.repository.PointHistoryRepository;
import shop.bookbom.shop.domain.pointhistory.service.PointHistoryService;

@Service
@RequiredArgsConstructor
public class PointHistoryServiceImpl implements PointHistoryService {
    private final MemberRepository memberRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<PointHistoryResponse> findPointHistory(Long memberId, Pageable pageable, ChangeReason changeReason) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        return pointHistoryRepository.getPointHistory(member, pageable, changeReason);
    }
}
