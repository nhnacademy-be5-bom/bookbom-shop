package shop.bookbom.shop.domain.cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static shop.bookbom.shop.common.TestUtils.getCart;
import static shop.bookbom.shop.common.TestUtils.getMember;
import static shop.bookbom.shop.common.TestUtils.getPointRate;
import static shop.bookbom.shop.common.TestUtils.getRank;
import static shop.bookbom.shop.common.TestUtils.getRole;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.bookbom.shop.domain.cart.entity.Cart;
import shop.bookbom.shop.domain.cart.repository.CartRepository;
import shop.bookbom.shop.domain.cart.service.impl.CartFindServiceImpl;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class CartFindServiceTest {
    @InjectMocks
    CartFindServiceImpl cartFindService;

    @Mock
    CartRepository cartRepository;

    @Mock
    MemberRepository memberRepository;


    @Test
    @DisplayName("장바구니 엔티티 조회")
    void getCartEntity() {
        // given
        Member member = getMember("test@email.com", getRole(), getRank(getPointRate()));
        ReflectionTestUtils.setField(member, "id", 1L);
        Cart cart = getCart(member);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(cartRepository.getCartFetchItems(member)).thenReturn(Optional.of(cart));

        // when
        Cart findCart = cartFindService.getCart(1L);

        // then
        ReflectionTestUtils.setField(cart, "id", 1L);
        assertThat(findCart.getId()).isEqualTo(1L);
        assertThat(findCart.getMember()).isEqualTo(member);
    }

    @Test
    @DisplayName("장바구니가 없다면 새로 만들어서 반환")
    void getCartEntityNotExists() {
        // given
        Member member = getMember("test@email.com", getRole(), getRank(getPointRate()));
        ReflectionTestUtils.setField(member, "id", 1L);
        Cart cart = getCart(member);
        ReflectionTestUtils.setField(cart, "id", 1L);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(cartRepository.getCartFetchItems(member)).thenReturn(Optional.empty());
        when(cartRepository.save(any())).thenReturn(cart);

        // when
        Cart findCart = cartFindService.getCart(1L);

        // then
        assertThat(findCart.getId()).isEqualTo(1L);
        assertThat(findCart.getMember()).isEqualTo(member);
    }
}
