package shop.bookbom.shop.domain.membercoupon.service;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.coupon.repository.CouponRepository;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.repository.MemberRepository;
import shop.bookbom.shop.domain.membercoupon.entity.CouponStatus;
import shop.bookbom.shop.domain.membercoupon.entity.MemberCoupon;
import shop.bookbom.shop.domain.membercoupon.repository.MemberCouponRepository;
import shop.bookbom.shop.domain.order.entity.Order;
import shop.bookbom.shop.domain.order.repository.OrderRepository;
import shop.bookbom.shop.domain.ordercoupon.entity.OrderCoupon;
import shop.bookbom.shop.domain.ordercoupon.exception.OrderCouponNotFoundException;
import shop.bookbom.shop.domain.ordercoupon.repository.OrderCouponRepository;

@Service
@RequiredArgsConstructor
public class MemberCouponServiceImpl implements MemberCouponService {
    private final OrderRepository orderRepository;
    private final OrderCouponRepository orderCouponRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;

    /**
     * 쿠폰 사용
     *
     * @param order
     * @param member
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void useCoupon(Order order, Member member) {
        OrderCoupon orderCoupon = orderCouponRepository.findByOrder(order)
                .orElseThrow(OrderCouponNotFoundException::new);

        MemberCoupon memberCoupon = memberCouponRepository.findByCouponAndMember(orderCoupon.getCoupon(), member);
        memberCoupon.updateCouponStatus(CouponStatus.USED);
        memberCoupon.updateUseDate(LocalDate.now());
        memberCouponRepository.save(memberCoupon);


    }

}
