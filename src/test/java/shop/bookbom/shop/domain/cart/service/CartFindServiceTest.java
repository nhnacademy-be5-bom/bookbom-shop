package shop.bookbom.shop.domain.cart.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.cart.entity.Cart;
import shop.bookbom.shop.domain.cart.repository.CartRepository;
import shop.bookbom.shop.domain.cartitem.entity.CartItem;
import shop.bookbom.shop.domain.member.entity.Member;

@ExtendWith(MockitoExtension.class)
class CartFindServiceTest {

    @Mock
    CartRepository cartRepository;

    @InjectMocks
    CartFindService cartFindService;


    @Test
    @DisplayName("장바구니 조회")
    void getCartAlreadyExist() {
        //given
        Member member = CartTestUtils.getMember();
        Book book = CartTestUtils.getBook();
        Cart cart = CartTestUtils.getCart(member);
        cart.addItem(CartItem.builder()
                .cart(cart)
                .book(book)
                .quantity(3)
                .build());
        when(cartRepository.getCartByMemberFetch(any())).thenReturn(Optional.of(cart));


        //when
        Cart findCart = cartFindService.getCart(member);
        CartItem cartItem = findCart.getCartItems().get(0);

        //then
        assertThat(cartItem.getBook().getTitle()).isEqualTo("bookTitle");
        assertThat(cartItem.getBook().getDescription()).isEqualTo("description");
        assertThat(cartItem.getCart()).isEqualTo(cart);
        assertThat(cartItem.getQuantity()).isEqualTo(3);
    }

    @Test
    @DisplayName("장바구니가 없을 땐 새로 만들어서 조회")
    void getCart() {
        //given
        Member member = CartTestUtils.getMember();
        when(cartRepository.getCartByMemberFetch(any())).thenReturn(Optional.empty());
        when(cartRepository.save(any())).thenReturn(Cart.builder().member(member).build());


        //when
        Cart findCart = cartFindService.getCart(member);

        //then
        assertThat(findCart.getMember()).isEqualTo(member);
    }
}