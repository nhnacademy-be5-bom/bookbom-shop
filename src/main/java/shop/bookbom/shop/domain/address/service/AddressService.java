package shop.bookbom.shop.domain.address.service;

import java.util.List;
import shop.bookbom.shop.domain.address.dto.response.AddressResponse;

public interface AddressService {
    /**
     * 회원의 주소록을 반환하는 메서드입니다.
     *
     * @param userId 회원 ID
     * @return 주소록
     */
    List<AddressResponse> getAddressBook(Long userId);

    /**
     * 주소를 저장하는 메서드입니다.
     *
     * @param userId        회원 ID
     * @param nickname      별칭
     * @param zipCode       우편번호
     * @param address       주소
     * @param addressDetail 상세주소
     */
    void saveAddress(Long userId, String nickname, String zipCode, String address, String addressDetail);
}
