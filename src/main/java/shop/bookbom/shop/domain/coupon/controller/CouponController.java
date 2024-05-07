package shop.bookbom.shop.domain.coupon.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonListResponse;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.coupon.dto.request.AddBookCouponRequest;
import shop.bookbom.shop.domain.coupon.dto.request.AddCategoryCouponRequest;
import shop.bookbom.shop.domain.coupon.dto.request.AddCouponRequest;
import shop.bookbom.shop.domain.coupon.dto.request.CouponInfoRequest;
import shop.bookbom.shop.domain.coupon.dto.response.CouponInfoResponse;
import shop.bookbom.shop.domain.coupon.service.CouponService;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @PostMapping("/generalCoupon/{id}")
    public CommonResponse<Void> addGeneralCoupon(@PathVariable("id") Long userId,
                                                 @RequestBody AddCouponRequest addCouponRequest) {
        couponService.addGeneralCoupon(addCouponRequest);
        return CommonResponse.success();
    }

    @PostMapping("/bookCoupon/{id}")
    public CommonResponse<Void> addGeneralCoupon(@PathVariable("id") Long userId,
                                                 @RequestBody AddBookCouponRequest addCouponRequest) {
        couponService.addBookCoupon(addCouponRequest);
        return CommonResponse.success();
    }

    @PostMapping("/categoryCoupon/{id}")
    public CommonResponse<Void> addCategoryCoupon(@PathVariable("id") Long userId,
                                                  @RequestBody AddCategoryCouponRequest addCouponRequest) {
        couponService.addCategoryCoupon(addCouponRequest);
        return CommonResponse.success();
    }

    @GetMapping("/couponAdmin/{id}")
    public CommonResponse<Page<CouponInfoResponse>> getCouponList(@PageableDefault Pageable pageable,
                                                                  @PathVariable("id") Long userId, @RequestBody
                                                                  CouponInfoRequest couponInfoRequest) {
        Page<CouponInfoResponse> couponInfoResponses = couponService.getCouponInfo(pageable, couponInfoRequest);
        return CommonResponse.successWithData(couponInfoResponses);
    }
}
