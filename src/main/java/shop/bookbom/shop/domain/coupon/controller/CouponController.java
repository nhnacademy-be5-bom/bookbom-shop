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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.annotation.Login;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.coupon.dto.request.AddBookCouponRequest;
import shop.bookbom.shop.domain.coupon.dto.request.AddCategoryCouponRequest;
import shop.bookbom.shop.domain.coupon.dto.request.AddCouponRequest;
import shop.bookbom.shop.domain.coupon.dto.response.CouponInfoResponse;
import shop.bookbom.shop.domain.coupon.dto.response.MyCouponInfoResponse;
import shop.bookbom.shop.domain.coupon.dto.response.MyCouponRecordResponse;
import shop.bookbom.shop.domain.coupon.service.CouponService;
import shop.bookbom.shop.domain.membercoupon.dto.request.IssueCouponRequest;
import shop.bookbom.shop.domain.users.dto.UserDto;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    /**
     * 관리자 페이지에서 일반 쿠폰을 등록할 때 사용합니다.
     *
     * @param addCouponRequest
     * @return
     */
    @PostMapping("/admin/generalCoupon")
    public CommonResponse<Void> addGeneralCoupon(@RequestBody @Valid AddCouponRequest addCouponRequest) {
        couponService.addGeneralCoupon(addCouponRequest);
        return CommonResponse.success();
    }

    /**
     * 관리자 페이지에서 책 쿠폰을 등록할 때 사용합니다.
     *
     * @param addCouponRequest
     * @return
     */
    @PostMapping("/admin/bookCoupon")
    public CommonResponse<Void> addGeneralCoupon(@RequestBody @Valid AddBookCouponRequest addCouponRequest) {
        couponService.addBookCoupon(addCouponRequest);
        return CommonResponse.success();
    }

    /**
     * 관리자 페이지에서 카테고리 쿠폰을 등록할 때 사용합니다.
     *
     * @param addCouponRequest 등록할 쿠폰 정보
     * @return 성공 응답
     */
    @PostMapping("/admin/categoryCoupon")
    public CommonResponse<Void> addCategoryCoupon(@RequestBody @Valid AddCategoryCouponRequest addCouponRequest) {
        couponService.addCategoryCoupon(addCouponRequest);
        return CommonResponse.success();
    }

    /**
     * 관리자 페이지에서 쿠폰을 조회할 때 사용합니다.
     *
     * @param pageable 페이징 처리
     * @param type 쿠폰 종류(일반, 도서, 카테고리)별로 조회 가능
     * @return 종류에 따른 쿠폰 목록
     */
    @GetMapping("/admin/coupons/{type}")
    public CommonResponse<Page<CouponInfoResponse>> getCouponList(@PageableDefault Pageable pageable,
                                                                  @PathVariable("type") String type) {
        Page<CouponInfoResponse> couponInfoResponses = couponService.getCouponInfo(pageable, type.toUpperCase());
        return CommonResponse.successWithData(couponInfoResponses);
    }

    /**
     * 관리자가 쿠폰을 발급할 때 사용합니다.
     *
     * @param issueCouponRequest 쿠폰 발급 요청 정보
     * @return 성공 응답
     */
    @PostMapping("/admin/coupons/issue")
    public CommonResponse<Void> issueCoupon(@RequestBody @Valid IssueCouponRequest issueCouponRequest) {
        couponService.addMemberCoupon(issueCouponRequest);
        return CommonResponse.success();
    }

    /**
     * 회원가입시 웰컴 쿠폰을 발급할 때 사용합니다.
     *
     * @param email 유저 이메일
     * @return 성공 응답
     */
    @GetMapping("/open/coupons/welcome")
    public CommonResponse<Void> issueWelcomeCoupon(@RequestParam String email){
        couponService.addWelcomeCoupon(email);
        return CommonResponse.success();
    }

    /**
     * 내 쿠폰함에서 쿠폰 목록을 불러오는 메서드입니다.
     *
     * @param pageable 페이징 처리
     * @param userDto 사용자 정보
     * @return 사용자 보유 쿠폰 정보
     */
    @GetMapping("/mycoupons")
    public  CommonResponse<Page<MyCouponInfoResponse>> getMyCouponList(@PageableDefault Pageable pageable, @Login UserDto userDto){
        return CommonResponse.successWithData(couponService.getMemberCouponInfo(pageable, userDto.getId())); //테스트
    }

    /**
     * 내 쿠폰함에서 쿠폰 발급/사용/만료 기록을 불러오는 메서드입니다.
     *
     * @param pageable 페이징 처리
     * //@param userDto 사용자 정보
     * @return 사용자 쿠폰 기록 정보
     */
    @GetMapping("/mycoupons/detail")
    public CommonResponse<Page<MyCouponRecordResponse>> getMyCouponRecodes(@PageableDefault Pageable pageable, @Login UserDto userDto){
        return CommonResponse.successWithData(couponService.getMemberRecodes(pageable, userDto.getId()));
    }
}
