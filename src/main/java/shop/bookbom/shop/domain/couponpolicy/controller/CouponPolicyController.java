package shop.bookbom.shop.domain.couponpolicy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/couponPolicy/{id}")
    public CommonResponse<Void> addCouponPolicy(@PathVariable("id") Long userId,
                                                @RequestBody CouponPolicyAddRequest request) {
        couponPolicyService.addCouponPolicy(request, userId);
        return CommonResponse.success();
    }

    @DeleteMapping("/couponPolicy/{id}")
    public CommonResponse<Void> deleteCouponPolicy(@PathVariable("id") Long userId,
                                                   @RequestBody CouponPolicyDeleteRequest request) {
        couponPolicyService.deleteCouponPolicy(request, userId);
        return CommonResponse.success();
    }

    @PutMapping("/couponPolicy/{id}")
    public CommonResponse<Void> updateCouponPolicy(@PathVariable("id") Long userId,
                                                   @RequestBody CouponPolicyInfoDto request) {
        couponPolicyService.updateCouponPolicy(request, userId);
        return CommonResponse.success();
    }

    @GetMapping("/couponPolicy/{id}")
    public CommonListResponse<CouponPolicyInfoDto> getCouponPolicyList(@PathVariable("id") Long userId) {
        return CommonListResponse.successWithList(couponPolicyService.getCouponPolicyInfo(userId));
    }

}
