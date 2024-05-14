package shop.bookbom.shop.domain.membercoupon.repository.impl;

import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import shop.bookbom.shop.domain.coupon.dto.CouponDto;
import shop.bookbom.shop.domain.coupon.entity.Coupon;
import shop.bookbom.shop.domain.coupon.entity.QCoupon;
import shop.bookbom.shop.domain.couponbook.dto.CouponBookDto;
import shop.bookbom.shop.domain.couponbook.entity.QCouponBook;
import shop.bookbom.shop.domain.couponcategory.dto.CouponCategoryDto;
import shop.bookbom.shop.domain.couponcategory.entity.QCouponCategory;
import shop.bookbom.shop.domain.couponpolicy.entity.QCouponPolicy;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.entity.QMember;
import shop.bookbom.shop.domain.membercoupon.dto.MemberCouponDto;
import shop.bookbom.shop.domain.membercoupon.entity.CouponStatus;
import shop.bookbom.shop.domain.membercoupon.entity.MemberCoupon;
import shop.bookbom.shop.domain.membercoupon.entity.QMemberCoupon;
import shop.bookbom.shop.domain.membercoupon.repository.MemberCouponRepositoryCustom;

@Repository
public class MemberCouponRepositoryCustomImpl extends QuerydslRepositorySupport
        implements MemberCouponRepositoryCustom {
    public MemberCouponRepositoryCustomImpl() {
        super(MemberCoupon.class);
    }

    private static final QMemberCoupon memberCoupon = QMemberCoupon.memberCoupon;
    private static final QCoupon coupon = QCoupon.coupon;
    private static final QMember member = QMember.member;
    private static final QCouponPolicy couponPolicy = QCouponPolicy.couponPolicy;
    private static final QCouponBook couponBook = QCouponBook.couponBook;
    private static final QCouponCategory couponCategory = QCouponCategory.couponCategory;

    @Override
    public List<MemberCouponDto> getAllMemberCoupons(Long memberId) {
        List<MemberCouponDto> memberCouponDtos = from(memberCoupon)
                .select(Projections.constructor(MemberCouponDto.class,
                        memberCoupon.id,
                        memberCoupon.status,
                        memberCoupon.issueDate,
                        memberCoupon.expireDate,
                        memberCoupon.useDate,
                        memberCoupon.coupon.id))
                .innerJoin(coupon).on(memberCoupon.coupon.eq(coupon))
                .join(member).on(member.eq(memberCoupon.member))
                .where(member.id.eq(memberId))
                .where(memberCoupon.status.eq(CouponStatus.NEW))
                .fetch();

        for (MemberCouponDto memberCouponDto : memberCouponDtos) {
            CouponDto couponDto = from(coupon)
                    .select(Projections.constructor(CouponDto.class,
                            coupon.id,
                            coupon.name,
                            coupon.type,
                            couponPolicy.minOrderCost,
                            couponPolicy.discountType,
                            couponPolicy.discountCost,
                            couponPolicy.maxDiscountCost))
                    .innerJoin(couponPolicy).on(couponPolicy.id.eq(coupon.couponPolicy.id))
                    .where(coupon.id.eq(memberCouponDto.getCouponId()))
                    .fetchOne();
            memberCouponDto.updateCouponDto(couponDto);

            List<CouponBookDto> couponBooks = from(couponBook)
                    .select(Projections.constructor(CouponBookDto.class,
                            couponBook.id,
                            couponBook.book.id))
                    .innerJoin(coupon).on(coupon.id.eq(couponBook.coupon.id))
                    .where(coupon.id.eq(memberCouponDto.getCouponId()))
                    .fetch();
            couponDto.updateCouponBooks(couponBooks);

            List<CouponCategoryDto> couponCategorys = from(couponCategory)
                    .select(Projections.constructor(CouponCategoryDto.class,
                            couponCategory.id,
                            couponCategory.category.id))
                    .innerJoin(coupon).on(coupon.id.eq(couponCategory.coupon.id))
                    .where(coupon.id.eq(memberCouponDto.getCouponId()))
                    .fetch();
            couponDto.updateCouponCategories(couponCategorys);

        }


        return memberCouponDtos;
    }

    @Override
    public MemberCoupon findByCouponAndMember(Coupon coupon1, Member member1) {
        return from(memberCoupon)
                .innerJoin(coupon).on(coupon.eq(memberCoupon.coupon))
                .innerJoin(member).on(member.eq(memberCoupon.member))
                .where(memberCoupon.status.eq(CouponStatus.NEW)
                        .and(coupon.eq(coupon1))
                        .and(member.eq(member1)))
                .fetchOne();

    }
}
