package shop.bookbom.shop.domain.pointrate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonListResponse;
import shop.bookbom.shop.domain.pointrate.repository.dto.PointRateResponse;
import shop.bookbom.shop.domain.pointrate.service.PointRateService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class PointRateController {
    private final PointRateService pointRateService;

    @GetMapping("/point-rate")
    public CommonListResponse<PointRateResponse> getAllPolicies() {
        return CommonListResponse.successWithList(pointRateService.getPointPolicies());
    }
}
