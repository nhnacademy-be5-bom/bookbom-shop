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

    /**
     * 기본 배송지를 변경하는 메서드입니다.
     *
     * @param userId    회원 ID
     * @param addressId 주소 ID
     */
    void updateDefaultAddress(Long userId, Long addressId);

    /**
     * 주소를 수정하는 메서드입니다.
     *
     * @param id            주소 ID
     * @param nickname      별칭
     * @param zipcode       우편번호
     * @param address       주소
     * @param addressDetail 상세주소
     */
    void updateAddress(Long id, String nickname, String zipcode, String address,
                       String addressDetail);

    /**
     * 동일한 주소가 있는지 확인하는 메서드입니다.
     *
     * @param memberId      회원 ID
     * @param zipCode       우편번호
     * @param address       주소
     * @param addressDetail 상세주소
     * @return 동일한 주소가 있으면 true, 없으면 false
     */
    boolean existsSameAddress(Long memberId, String zipCode, String address, String addressDetail);
}
