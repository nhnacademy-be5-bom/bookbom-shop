package shop.bookbom.shop.domain.pointhistory.controller;

import static shop.bookbom.shop.common.CommonResponse.successWithData;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.pointhistory.dto.response.PointHistoryResponse;
import shop.bookbom.shop.domain.pointhistory.entity.ChangeReason;
import shop.bookbom.shop.domain.pointhistory.exception.InvalidChangeReasonException;
import shop.bookbom.shop.domain.pointhistory.service.PointHistoryService;

@RestController
@RequiredArgsConstructor
public class PointHistoryController {
    private final PointHistoryService pointHistoryService;

    /**
     * 파라미터로 온 reason을 ChangeReason으로 변환하는 메서드입니다.
     *
     * @param changeReason 포인트 변동 사유
     * @return ChangeReason
     */
    private static ChangeReason getChangeReason(String changeReason) {
        return Arrays.stream(ChangeReason.values())
                .filter(it -> it.name().equals(changeReason))
                .findFirst()
                .orElseThrow(InvalidChangeReasonException::new);
    }

    @GetMapping("/point-history")
    public CommonResponse<Page<PointHistoryResponse>> getPointHistory(
            @RequestParam Long userId,
            Pageable pageable,
            @RequestParam(value = "reason", required = false) String reason
    ) {
        if (reason == null) {
            return successWithData(pointHistoryService.findPointHistory(userId, pageable, null));
        }
        return successWithData(pointHistoryService.findPointHistory(userId, pageable, getChangeReason(reason)));
    }
}
