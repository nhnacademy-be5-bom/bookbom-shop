package shop.bookbom.shop.domain.coupon.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonListResponse;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.coupon.dto.request.AddBookCouponRequest;
import shop.bookbom.shop.domain.coupon.dto.request.AddCategoryCouponRequest;
import shop.bookbom.shop.domain.coupon.dto.request.AddCouponRequest;
import shop.bookbom.shop.domain.coupon.dto.response.CouponInfoResponse;
import shop.bookbom.shop.domain.coupon.dto.response.CouponIssueResponse;
import shop.bookbom.shop.domain.coupon.service.CouponService;
import shop.bookbom.shop.domain.membercoupon.dto.request.IssueCouponRequest;

@RestController
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @PostMapping("/admin/generalCoupon/{id}")
    public CommonResponse<Void> addGeneralCoupon(@PathVariable("id") Long userId,
                                                 @RequestBody @Valid AddCouponRequest addCouponRequest) {
        couponService.addGeneralCoupon(addCouponRequest);
        return CommonResponse.success();
    }

    @PostMapping("/admin/bookCoupon/{id}")
    public CommonResponse<Void> addGeneralCoupon(@PathVariable("id") Long userId,
                                                 @RequestBody @Valid AddBookCouponRequest addCouponRequest) {
        couponService.addBookCoupon(addCouponRequest);
        return CommonResponse.success();
    }

    @PostMapping("/admin/categoryCoupon/{id}")
    public CommonResponse<Void> addCategoryCoupon(@PathVariable("id") Long userId,
                                                  @RequestBody @Valid AddCategoryCouponRequest addCouponRequest) {
        couponService.addCategoryCoupon(addCouponRequest);
        return CommonResponse.success();
    }

    @GetMapping("/admin/coupons/{type}/{id}")
    public CommonResponse<Page<CouponInfoResponse>> getCouponList(@PageableDefault Pageable pageable,
                                                                  @PathVariable("id") Long userId,
                                                                  @PathVariable("type") String type) {
        Page<CouponInfoResponse> couponInfoResponses = couponService.getCouponInfo(pageable, type.toUpperCase());
        return CommonResponse.successWithData(couponInfoResponses);
    }

    /**
     * 쿠폰을 발급합니다.
     *
     * @param userId             유저
     * @param issueCouponRequest 쿠폰 발급 요청 정보
     * @return 성공 응답
     */
    @PostMapping("/admin/coupons/issue/{id}")
    public CommonResponse<Void> issueCoupon(@PathVariable("id") Long userId,
                                            @RequestBody @Valid IssueCouponRequest issueCouponRequest) {
        couponService.addMemberCoupon(issueCouponRequest);
        return CommonResponse.success();
    }

    ;

    /**
     * 쿠폰 발급에서 발급 가능한 쿠폰 이름 목록을 보여줄 때 사용됩니다.
     *
     * @param userId 유저
     * @return 쿠폰 목록 정보(쿠폰 이름, 쿠폰 id, 쿠폰 타입)
     */
    @GetMapping("/admin/coupons/issue/{id}")
    public CommonListResponse<CouponIssueResponse> getCouponNameList(@PathVariable("id") Long userId) {
        return CommonListResponse.successWithList(couponService.getCouponName());
    }
}
