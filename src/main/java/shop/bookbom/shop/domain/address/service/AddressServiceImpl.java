package shop.bookbom.shop.domain.address.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.address.dto.response.AddressResponse;
import shop.bookbom.shop.domain.address.entity.Address;
import shop.bookbom.shop.domain.address.repository.AddressRepository;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.exception.MemberNotFoundException;
import shop.bookbom.shop.domain.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
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
}
