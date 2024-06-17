package shop.bookbom.shop.domain.address.repository.impl;

import static shop.bookbom.shop.domain.address.entity.QAddress.address1;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import shop.bookbom.shop.domain.address.repository.AddressRepositoryCustom;
import shop.bookbom.shop.domain.member.entity.Member;

@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsSameAddress(Member member, String address, String addressDetail, String zipCode) {
        Integer result = queryFactory
                .selectOne()
                .from(address1)
                .where(
                        address1.member.eq(member)
                                .and(address1.address.eq(address))
                                .and(address1.addressDetail.eq(addressDetail))
                )
                .fetchFirst();
        return result != null;
    }
}
