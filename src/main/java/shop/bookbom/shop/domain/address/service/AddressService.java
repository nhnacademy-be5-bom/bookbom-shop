package shop.bookbom.shop.domain.address.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.address.dto.request.AddressRequest;
import shop.bookbom.shop.domain.address.entity.Address;
import shop.bookbom.shop.domain.address.repository.AddressRepository;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.repository.MemberRepository;
import shop.bookbom.shop.domain.users.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void addAddress(AddressRequest addressRequest){
        Member member = memberRepository.findById(addressRequest.getUserId())
                .orElseThrow(UserNotFoundException::new);
        Address address = Address.builder()
                .nickname(addressRequest.getNickName())
                .zipCode(addressRequest.getZipCode())
                .address(addressRequest.getAddress())
                .addressDetail(addressRequest.getAddressDetail())
                .defaultAddress(addressRequest.isDefaultAddress())
                .member(member)
                .build();

        addressRepository.save(address);
    }
}
