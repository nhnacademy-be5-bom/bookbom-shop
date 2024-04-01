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
import shop.bookbom.shop.domain.book.repository.BookRepository;
import shop.bookbom.shop.domain.cart.entity.Cart;
import shop.bookbom.shop.domain.cartitem.entity.CartItem;
import shop.bookbom.shop.domain.member.entity.Member;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    CartService cartService;

    @Mock
    CartFindService cartFindService;

    @Mock
    BookRepository bookRepository;


    @Test
    @DisplayName("장바구니에 없던 도서 추가")
    void addCartEmpty() {
        //given
        Book book = CartTestUtils.getBook();
        Member member = CartTestUtils.getMember();
        Cart cart = CartTestUtils.getCart(member);
        when(cartFindService.getCart(any())).thenReturn(cart);
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        // when
        Cart result = cartService.addCart(CartTestUtils.getCartAddRequest(), member);
        CartItem cartItem = result.getCartItems().get(0);

        //then
        assertThat(cartItem.getBook().getTitle()).isEqualTo("bookTitle");
        assertThat(cartItem.getBook().getDescription()).isEqualTo("description");
        assertThat(cartItem.getCart()).isEqualTo(cart);
        assertThat(cartItem.getQuantity()).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니에 있던 도서 추가")
    void addCartExists() {
        // given
        Member member = CartTestUtils.getMember();
        Book book = CartTestUtils.getBook();
        Cart cart = CartTestUtils.getCart(member);
        cart.addItem(CartItem.builder()
                .cart(cart)
                .book(book)
                .quantity(3)
                .build());
        when(cartFindService.getCart(any())).thenReturn(cart);
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        // when
        Cart result = cartService.addCart(CartTestUtils.getCartAddRequest(), member);
        CartItem cartItem = result.getCartItems().get(0);

        //then
        assertThat(cartItem.getBook().getTitle()).isEqualTo("bookTitle");
        assertThat(cartItem.getBook().getDescription()).isEqualTo("description");
        assertThat(cartItem.getCart()).isEqualTo(cart);
        assertThat(cartItem.getQuantity()).isEqualTo(5);
    }
}