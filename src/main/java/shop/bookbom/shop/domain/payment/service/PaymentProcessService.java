package shop.bookbom.shop.domain.payment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.service.MemberService;
import shop.bookbom.shop.domain.membercoupon.service.MemberCouponService;
import shop.bookbom.shop.domain.order.entity.Order;
import shop.bookbom.shop.domain.order.service.OrderService;
import shop.bookbom.shop.domain.payment.dto.request.PaymentRequest;
import shop.bookbom.shop.domain.pointhistory.service.PointHistoryService;

@Service
@RequiredArgsConstructor
public class PaymentProcessService {
    private final PaymentService paymentService;
    private final MemberCouponService memberCouponService;
    private final MemberService memberService;
    private final PointHistoryService pointHistoryService;
    private final OrderService orderService;


    @Transactional
    public Long proceesPayment(PaymentRequest paymentRequest) {
        Order order = paymentService.getPaymnetConfirm(paymentRequest);
        //트랜잭션 분리
        Member member = memberService.getMemberById(order.getUser().getId());
        //비회원이 아니면
        if (member.getRole().getId() != 1L) {
            if (order.getUsedCouponCost() != 0) {
                //쿠폰 사용
                memberCouponService.useCoupon(order, member);
            }
            if (order.getUsedPoint() != 0) {
                //포인트 사용
                pointHistoryService.decreasePoint(member, order.getUsedPoint());
            }

        }
        return order.getId();
    }

    @Transactional
    public Long processFreePayment(String orderNumber) {
        Order order = orderService.getOrderByOrderNumber(orderNumber);
        Member member = memberService.getMemberById(order.getUser().getId());
        if (order.getUsedCouponCost() != 0) {
            //쿠폰 사용
            memberCouponService.useCoupon(order, member);
        }
        if (order.getUsedPoint() != 0) {
            //포인트 사용
            pointHistoryService.decreasePoint(member, order.getUsedPoint());
        }
        //도서 포인트 적립
        pointHistoryService.earnPointByBook(member, order.getTotalCost());
        pointHistoryService.earnPointByRank(member, order.getTotalCost());

        return order.getId();
    }
}
