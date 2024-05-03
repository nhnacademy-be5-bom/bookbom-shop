package shop.bookbom.shop.domain.pointhistory.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static shop.bookbom.shop.common.TestUtils.getMember;
import static shop.bookbom.shop.common.TestUtils.getPointHistoryResponse;
import static shop.bookbom.shop.common.TestUtils.getPointRate;
import static shop.bookbom.shop.common.TestUtils.getRank;
import static shop.bookbom.shop.common.TestUtils.getRole;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import shop.bookbom.shop.domain.member.repository.MemberRepository;
import shop.bookbom.shop.domain.pointhistory.dto.response.PointHistoryResponse;
import shop.bookbom.shop.domain.pointhistory.entity.ChangeReason;
import shop.bookbom.shop.domain.pointhistory.entity.PointHistoryDetail;
import shop.bookbom.shop.domain.pointhistory.repository.PointHistoryRepository;
import shop.bookbom.shop.domain.pointhistory.service.impl.PointHistoryServiceImpl;

@ExtendWith(MockitoExtension.class)
class PointHistoryServiceTest {

    @Mock
    PointHistoryRepository pointHistoryRepository;

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    PointHistoryServiceImpl pointHistoryService;

    @Test
    @DisplayName("포인트 내역 조회")
    void findPointHistory() {
        //given
        when(memberRepository.findById(any())).thenReturn(
                Optional.of(getMember("email@test.com", getRole(), getRank(getPointRate()))));
        PointHistoryResponse response = getPointHistoryResponse(ChangeReason.USE);
        PageRequest pageRequest = PageRequest.of(0, 5);
        when(pointHistoryRepository.getPointHistory(any(), any(), any()))
                .thenReturn(new PageImpl<>(List.of(response), pageRequest, 1));
        //when
        Page<PointHistoryResponse> pointHistory = pointHistoryService.findPointHistory(1L, pageRequest, null);
        //then
        List<PointHistoryResponse> content = pointHistory.getContent();
        PointHistoryResponse result = content.get(0);
        assertThat(content).hasSize(1);
        assertThat(result.getReason()).isEqualTo(ChangeReason.USE.name());
        assertThat(result.getChangePoint()).isEqualTo(1000);
        assertThat(result.getDetail()).isEqualTo(PointHistoryDetail.ORDER_EARN.name());
    }
}
