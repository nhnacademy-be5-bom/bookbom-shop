package shop.bookbom.shop.domain.couponpolicy.repository.impl;

import static shop.bookbom.shop.domain.couponpolicy.entity.QCouponPolicy.couponPolicy;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.couponpolicy.dto.CouponPolicyInfoDto;
import shop.bookbom.shop.domain.couponpolicy.repository.CouponPolicyCustomRepository;

public class CouponPolicyCustomRepositoryImpl implements CouponPolicyCustomRepository {
    private final JPAQueryFactory queryFactory;

    public CouponPolicyCustomRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    @Transactional
    public long updateCouponPolicyInfo(CouponPolicyInfoDto couponPolicyInfoDto) {
        return queryFactory.update(couponPolicy)
                .where(couponPolicy.id.eq(couponPolicyInfoDto.getCouponPolicyId()))
                .set(couponPolicy.minOrderCost, couponPolicyInfoDto.getMinOrderCost())
                .set(couponPolicy.discountType, couponPolicyInfoDto.getDiscountType())
                .set(couponPolicy.discountCost, couponPolicyInfoDto.getDiscountCost())
                .set(couponPolicy.maxDiscountCost, couponPolicyInfoDto.getMaxDiscountCost())
                .execute();
    }
}
