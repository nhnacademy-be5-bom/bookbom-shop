package shop.bookbom.shop.domain.address.repository;

import shop.bookbom.shop.domain.address.entity.Address;
import shop.bookbom.shop.domain.member.entity.Member;

public interface AddressRepositoryCustom {
    /**
     * 동일한 주소가 있는지 확인하는 메서드입니다.
     *
     * @param member        회원
     * @param address       주소
     * @param addressDetail 상세 주소
     * @param zipCode       우편번호
     * @return 동일한 주소가 있으면 반환하고 없으면 null을 반환합니다.
     */
    Address findSameAddress(Member member, String address, String addressDetail, String zipCode);
}
