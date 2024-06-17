package shop.bookbom.shop.domain.coupon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.bookbom.shop.domain.coupon.dto.response.CouponInfoResponse;
import shop.bookbom.shop.domain.coupon.entity.CouponType;

public interface CouponCustomRepository {
    Page<CouponInfoResponse> getCouponInfoList(Pageable pageable, CouponType type);
}
