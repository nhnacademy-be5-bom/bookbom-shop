package shop.bookbom.shop.domain.cart.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.cart.entity.Cart;
import shop.bookbom.shop.domain.cart.repository.CartRepository;
import shop.bookbom.shop.domain.cart.service.CartFindService;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.exception.MemberNotFoundException;
import shop.bookbom.shop.domain.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class CartFindServiceImpl implements CartFindService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Cart getCart(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(MemberNotFoundException::new);
        return cartRepository.getCartFetchItems(member)
                .orElseGet(() -> cartRepository.save(Cart.builder()
                        .member(member)
                        .build()));
    }
}
