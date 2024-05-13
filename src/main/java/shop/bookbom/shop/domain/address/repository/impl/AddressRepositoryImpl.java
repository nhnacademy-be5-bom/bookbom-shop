package shop.bookbom.shop.domain.address.repository.impl;

import static shop.bookbom.shop.domain.address.entity.QAddress.address1;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import shop.bookbom.shop.domain.address.entity.Address;
import shop.bookbom.shop.domain.address.repository.AddressRepositoryCustom;
import shop.bookbom.shop.domain.member.entity.Member;

@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Address findSameAddress(Member member, String address, String addressDetail, String zipCode) {
        return queryFactory
                .select(address1)
                .from(address1)
                .where(
                        address1.member.eq(member)
                                .and(address1.zipCode.eq(zipCode))
                                .and(address1.address.eq(address))
                                .and(address1.addressDetail.eq(addressDetail))
                )
                .fetchOne();
    }
}
