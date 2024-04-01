package shop.bookbom.shop.domain.cart.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.cart.entity.Cart;
import shop.bookbom.shop.domain.cart.repository.CartRepository;
import shop.bookbom.shop.domain.member.entity.Member;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartFindService {
    private final CartRepository cartRepository;

    @Transactional
    public Cart getCart(Member member) {
        return cartRepository.getCartByMemberFetch(member)
                .orElseGet(() -> cartRepository.save(Cart.builder()
                        .member(member)
                        .build()));
    }
}
