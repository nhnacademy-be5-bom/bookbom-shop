package shop.bookbom.shop.domain.address.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.address.dto.response.AddressResponse;
import shop.bookbom.shop.domain.address.entity.Address;
import shop.bookbom.shop.domain.address.exception.AddressLimitExceedException;
import shop.bookbom.shop.domain.address.exception.AddressNotFoundException;
import shop.bookbom.shop.domain.address.repository.AddressRepository;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.exception.MemberNotFoundException;
import shop.bookbom.shop.domain.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private static final int MAX_ADDRESS_COUNT = 10;
    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AddressResponse> getAddressBook(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(MemberNotFoundException::new);
        List<Address> addresses = addressRepository.findByMember(member);
        return addresses.stream()
                .map(AddressResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveAddress(Long userId, String nickname, String zipCode, String address, String addressDetail) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(MemberNotFoundException::new);
        checkAddressCount(member);
        Address newAddress = Address.builder()
                .member(member)
                .nickname(nickname)
                .zipCode(zipCode)
                .address(address)
                .addressDetail(addressDetail)
                .build();
        addressRepository.save(newAddress);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsSameAddress(Long memberId, String zipCode, String address, String addressDetail) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        return addressRepository.existsSameAddress(member, zipCode, address, addressDetail);
    }

    @Override
    @Transactional
    public void updateDefaultAddress(Long userId, Long addressId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(MemberNotFoundException::new);
        List<Address> addresses = addressRepository.findByMember(member);
        Address findAddress = addresses.stream().filter(a -> a.getId().equals(addressId))
                .findFirst()
                .orElseThrow(AddressNotFoundException::new);
        addresses.forEach(a -> a.updateDefaultAddress(a.equals(findAddress)));
    }

    @Override
    @Transactional
    public void updateAddress(Long id, String nickname, String zipcode, String address,
                              String addressDetail) {
        Address findAddress = addressRepository.findById(id)
                .orElseThrow(AddressNotFoundException::new);
        findAddress.updateAddress(nickname, zipcode, address, addressDetail);
    }

    /**
     * 주소록 갯수를 확인해 최대 주소록 갯수를 초과하면 예외를 발생시키는 메서드입니다.
     *
     * @param member 회원
     */
    private void checkAddressCount(Member member) {
        long count = addressRepository.countByMember(member);
        if (count >= MAX_ADDRESS_COUNT) {
            throw new AddressLimitExceedException();
        }
    }
}
