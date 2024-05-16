package shop.bookbom.shop.domain.membercoupon.service;

import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.order.entity.Order;

public interface MemberCouponService {
    void useCoupon(Order order, Member member);
}
