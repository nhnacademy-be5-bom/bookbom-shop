package shop.bookbom.shop.domain.coupon.service;

import shop.bookbom.shop.domain.coupon.dto.request.AddBookCouponRequest;
import shop.bookbom.shop.domain.coupon.dto.request.AddCategoryCouponRequest;
import shop.bookbom.shop.domain.coupon.dto.request.AddCouponRequest;
import shop.bookbom.shop.domain.coupon.dto.request.CouponInfoRequest;

public interface CouponService {
    void addGeneralCoupon(AddCouponRequest addCouponRequest);

    void addBookCoupon(AddBookCouponRequest addBookCouponRequest);

    void addCategoryCoupon(AddCategoryCouponRequest addCategoryCouponRequest);

    void getCouponInfo();
}
