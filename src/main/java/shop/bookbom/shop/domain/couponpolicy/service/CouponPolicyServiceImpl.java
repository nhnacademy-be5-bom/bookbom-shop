package shop.bookbom.shop.domain.couponpolicy.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.couponpolicy.dto.request.CouponPolicyAddRequest;
import shop.bookbom.shop.domain.couponpolicy.dto.request.CouponPolicyDeleteRequest;
import shop.bookbom.shop.domain.couponpolicy.dto.CouponPolicyInfoDto;
import shop.bookbom.shop.domain.couponpolicy.entity.CouponPolicy;
import shop.bookbom.shop.domain.couponpolicy.exception.CouponPolicyNotFoundException;
import shop.bookbom.shop.domain.couponpolicy.repository.CouponPolicyRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponPolicyServiceImpl implements CouponPolicyService {
    private final CouponPolicyRepository couponPolicyRepository;

    @Override
    public void addCouponPolicy(CouponPolicyAddRequest request, Long userId) {
        CouponPolicy couponPolicy = CouponPolicy.builder()
                .discountType(request.getDiscountType())
                .discountCost(request.getDiscountCost())
                .minOrderCost(request.getMinOrderCost())
                .maxDiscountCost(request.getMaxDiscountCost())
                .build();

        couponPolicyRepository.save(couponPolicy);
    }

    @Override
    public void deleteCouponPolicy(CouponPolicyDeleteRequest request, Long userId) {
        CouponPolicy couponPolicy = couponPolicyRepository.findById(request.getCouponPolicyId())
                .orElseThrow(CouponPolicyNotFoundException::new);
        //couponPolicy를 정책으로 하는 쿠폰이 있는 경우 삭제 금지

        couponPolicyRepository.delete(couponPolicy);
    }

    @Override
    public void updateCouponPolicy(CouponPolicyInfoDto request, Long userId) {
        couponPolicyRepository.findById(request.getCouponPolicyId())
                .orElseThrow(CouponPolicyNotFoundException::new);
        couponPolicyRepository.updateCouponPolicyInfo(request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponPolicyInfoDto> getCouponPolicyInfo(Long userId) {
        List<CouponPolicy> couponPolicyList = couponPolicyRepository.findAll();
        List<CouponPolicyInfoDto> policyInfoResponseList = new ArrayList<>();
        couponPolicyList.forEach(policy -> {
            CouponPolicyInfoDto couponPolicyInfoDto = CouponPolicyInfoDto.from(policy);
            policyInfoResponseList.add(couponPolicyInfoDto);
        });
        return policyInfoResponseList;
    }
}
