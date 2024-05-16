package shop.bookbom.shop.domain.couponpolicy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonListResponse;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.couponpolicy.dto.CouponPolicyInfoDto;
import shop.bookbom.shop.domain.couponpolicy.dto.request.CouponPolicyAddRequest;
import shop.bookbom.shop.domain.couponpolicy.dto.request.CouponPolicyDeleteRequest;
import shop.bookbom.shop.domain.couponpolicy.service.CouponPolicyService;

@Slf4j
@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class CouponPolicyController {
    private final CouponPolicyService couponPolicyService;

    @PostMapping("/couponPolicy")
    public CommonResponse<Void> addCouponPolicy(@RequestBody CouponPolicyAddRequest request) {
        couponPolicyService.addCouponPolicy(request);
        return CommonResponse.success();
    }

    @DeleteMapping("/couponPolicy")
    public CommonResponse<Void> deleteCouponPolicy(@RequestBody CouponPolicyDeleteRequest request) {
        couponPolicyService.deleteCouponPolicy(request);
        return CommonResponse.success();
    }

    @PutMapping("/couponPolicy")
    public CommonResponse<Void> updateCouponPolicy(@RequestBody CouponPolicyInfoDto request) {
        couponPolicyService.updateCouponPolicy(request);
        return CommonResponse.success();
    }

    @GetMapping("/couponPolicy")
    public CommonListResponse<CouponPolicyInfoDto> getCouponPolicyList() {
        return CommonListResponse.successWithList(couponPolicyService.getCouponPolicyInfo());
    }

}
