package shop.bookbom.shop.domain.member.service.impl;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.address.entity.Address;
import shop.bookbom.shop.domain.address.repository.AddressRepository;
import shop.bookbom.shop.domain.member.dto.request.SignUpRequest;
import shop.bookbom.shop.domain.member.dto.response.MemberInfoResponse;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.entity.MemberStatus;
import shop.bookbom.shop.domain.member.repository.MemberRepository;
import shop.bookbom.shop.domain.member.service.MemberService;
import shop.bookbom.shop.domain.pointhistory.entity.ChangeReason;
import shop.bookbom.shop.domain.pointhistory.entity.PointHistory;
import shop.bookbom.shop.domain.pointhistory.entity.PointHistoryDetail;
import shop.bookbom.shop.domain.pointhistory.repository.PointHistoryRepository;
import shop.bookbom.shop.domain.pointrate.entity.PointRate;
import shop.bookbom.shop.domain.pointrate.exception.PointRateNotFoundException;
import shop.bookbom.shop.domain.pointrate.repository.PointRateRepository;
import shop.bookbom.shop.domain.rank.entity.Rank;
import shop.bookbom.shop.domain.rank.repository.RankRepository;
import shop.bookbom.shop.domain.role.entity.Role;
import shop.bookbom.shop.domain.role.repository.RoleRepository;
import shop.bookbom.shop.domain.users.exception.RoleNotFoundException;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private static final String ROLE_MEMBER = "ROLE_MEMBER";
    private static final String STANDARD_RANK = "STANDARD";
    private static final String SIGNUP_POINT_RATE = "회원가입";
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final AddressRepository addressRepository;
    private final RankRepository rankRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final PointRateRepository pointRateRepository;

    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberInfo(Long id) {
        return memberRepository.findMemberInfo(id);
    }

    @Override
    @Transactional
    public void save(SignUpRequest signUpRequest) {
        Role role = roleRepository.findByName(ROLE_MEMBER)
                .orElseThrow(RoleNotFoundException::new);
        PointRate pointRate = pointRateRepository.findByName(SIGNUP_POINT_RATE)
                .orElseThrow(PointRateNotFoundException::new);
        Rank rank = rankRepository.getRankByNameFetchPointRate(STANDARD_RANK);

        Member member = Member.builder()
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .name(signUpRequest.getName())
                .nickname(signUpRequest.getNickname())
                .birthDate(signUpRequest.getBirthDate())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .role(role)
                .rank(rank)
                .point(pointRate.getEarnPoint())
                .status(MemberStatus.ACTIVE)
                .build();
        memberRepository.save(member);

        PointHistory pointHistory = PointHistory.builder()
                .member(member)
                .changePoint(pointRate.getEarnPoint())
                .changeReason(ChangeReason.EARN)
                .changeDate(LocalDateTime.now())
                .detail(PointHistoryDetail.SIGN_UP)
                .build();
        pointHistoryRepository.save(pointHistory);

        Address address = Address.builder()
                .zipCode(signUpRequest.getAddressNumber())
                .address(signUpRequest.getAddress())
                .addressDetail(signUpRequest.getAddressDetail())
                .defaultAddress(true)
                .member(member)
                .build();
        addressRepository.save(address);
    }
}
