package shop.bookbom.shop.domain.pointrate.controller;

import static shop.bookbom.shop.common.CommonListResponse.successWithList;
import static shop.bookbom.shop.common.CommonResponse.successWithData;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonListResponse;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;
import shop.bookbom.shop.domain.pointrate.dto.request.PointRateUpdateRequest;
import shop.bookbom.shop.domain.pointrate.repository.dto.PointRateResponse;
import shop.bookbom.shop.domain.pointrate.service.PointRateService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class PointRateController {
    private final PointRateService pointRateService;

    @GetMapping("/point-rate")
    public CommonListResponse<PointRateResponse> getAllPolicies() {
        return successWithList(pointRateService.getPointPolicies());
    }

    @PutMapping("/point-rate/{id}")
    public CommonResponse<PointRateResponse> updatePolicy(
            @PathVariable("id") Long id,
            @RequestBody @Valid PointRateUpdateRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new BaseException(ErrorCode.COMMON_INVALID_PARAMETER,
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        PointRateResponse response = pointRateService.updatePolicy(id, request.getEarnType(), request.getEarnPoint());
        return successWithData(response);
    }
}
