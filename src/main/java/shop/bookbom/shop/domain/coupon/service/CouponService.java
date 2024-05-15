package shop.bookbom.shop.domain.coupon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.bookbom.shop.domain.coupon.dto.request.AddBookCouponRequest;
import shop.bookbom.shop.domain.coupon.dto.request.AddCategoryCouponRequest;
import shop.bookbom.shop.domain.coupon.dto.request.AddCouponRequest;
import shop.bookbom.shop.domain.coupon.dto.response.CouponInfoResponse;
import shop.bookbom.shop.domain.coupon.dto.response.MyCouponInfoResponse;
import shop.bookbom.shop.domain.coupon.dto.response.MyCouponRecordResponse;
import shop.bookbom.shop.domain.membercoupon.dto.request.IssueCouponRequest;

public interface CouponService {
    void addGeneralCoupon(AddCouponRequest addCouponRequest);

    void addBookCoupon(AddBookCouponRequest addBookCouponRequest);

    void addCategoryCoupon(AddCategoryCouponRequest addCategoryCouponRequest);

    Page<CouponInfoResponse> getCouponInfo(Pageable pageable, String type);

    void addMemberCoupon(IssueCouponRequest issueCouponRequest);

    void addWelcomeCoupon(String email);

    Page<MyCouponInfoResponse> getMemberCouponInfo(Pageable pageable, Long userId);

    Page<MyCouponRecordResponse> getMemberRecodes(Pageable pageable, Long userId);
}
