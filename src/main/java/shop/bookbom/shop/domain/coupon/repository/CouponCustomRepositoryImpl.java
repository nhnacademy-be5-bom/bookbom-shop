package shop.bookbom.shop.domain.coupon.repository;

import static shop.bookbom.shop.domain.book.entity.QBook.book;
import static shop.bookbom.shop.domain.category.entity.QCategory.category;
import static shop.bookbom.shop.domain.coupon.entity.QCoupon.coupon;
import static shop.bookbom.shop.domain.couponbook.entity.QCouponBook.couponBook;
import static shop.bookbom.shop.domain.couponcategory.entity.QCouponCategory.couponCategory;
import static shop.bookbom.shop.domain.couponpolicy.entity.QCouponPolicy.couponPolicy;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import shop.bookbom.shop.domain.coupon.dto.response.CouponInfoResponse;
import shop.bookbom.shop.domain.coupon.entity.CouponType;

public class CouponCustomRepositoryImpl implements CouponCustomRepository {
    private final JPAQueryFactory queryFactory;

    public CouponCustomRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<CouponInfoResponse> getCouponInfoList(Pageable pageable, CouponType type) {
        List<CouponInfoResponse> couponInfoList = queryFactory.select(
                        Projections.fields(
                                CouponInfoResponse.class,
                                coupon.id,
                                coupon.name,
                                coupon.type,
                                couponPolicy.discountType,
                                couponPolicy.discountCost,
                                couponPolicy.minOrderCost,
                                couponPolicy.maxDiscountCost,
                                book.title,
                                category.name.as("categoryName")
                        )
                )
                .from(coupon)
                .leftJoin(couponPolicy).on(coupon.couponPolicy.id.eq(couponPolicy.id))
                .leftJoin(couponBook).on(coupon.id.eq(couponBook.coupon.id))
                .leftJoin(book).on(couponBook.book.id.eq(book.id))
                .leftJoin(couponCategory).on(coupon.id.eq(couponCategory.coupon.id))
                .leftJoin(category).on(couponCategory.category.id.eq(category.id))
                .where(
                        coupon.type.eq(type)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(coupon.count())
                .from(coupon)
                .where(coupon.type.eq(type));

        return PageableExecutionUtils.getPage(couponInfoList, pageable, countQuery::fetchOne);
    }

}
