package shop.bookbom.shop.domain.cart.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static shop.bookbom.shop.domain.cart.service.CartTestUtils.getBook;
import static shop.bookbom.shop.domain.cart.service.CartTestUtils.getCart;
import static shop.bookbom.shop.domain.cart.service.CartTestUtils.getMember;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.repository.BookRepository;
import shop.bookbom.shop.domain.cart.entity.Cart;
import shop.bookbom.shop.domain.cartitem.entity.CartItem;
import shop.bookbom.shop.domain.cartitem.exception.CartItemNotFoundException;
import shop.bookbom.shop.domain.cartitem.repository.CartItemRepository;
import shop.bookbom.shop.domain.member.entity.Member;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    CartService cartService;

    @Mock
    CartFindService cartFindService;

    @Mock
    BookRepository bookRepository;

    @Mock
    CartItemRepository cartItemRepository;


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


    @Test
    @DisplayName("장바구니 목록 중 삭제")
    void delete() throws Exception {
        //given
        CartItem cartItem = CartItem.builder()
                .cart(getCart(getMember()))
                .book(getBook())
                .build();
        ReflectionTestUtils.setField(cartItem, "id", 1L);
        when(cartItemRepository.findById(any())).thenReturn(Optional.of(cartItem));

        //when
        cartService.deleteItem(1L);

        //then
        verify(cartItemRepository).delete(cartItem);
    }

    @Test
    @DisplayName("장바구니 목록 중 삭제 예외")
    void deleteNotExistsCartItem() throws Exception {
        //given
        when(cartItemRepository.findById(any())).thenReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> cartService.deleteItem(1L))
                .isInstanceOf(CartItemNotFoundException.class);
    }


    @Test
    @DisplayName("장바구니 도서 수량 수정")
    void updateQuantity() throws Exception {
        //given
        CartItem cartItem = CartItem.builder()
                .cart(getCart(getMember()))
                .book(getBook())
                .quantity(3)
                .build();
        ReflectionTestUtils.setField(cartItem, "id", 1L);
        when(cartItemRepository.findById(1L)).thenReturn(Optional.of(cartItem));

        //when
        int quantity = cartService.updateQuantity(1L, 3);

        //then
        assertThat(quantity).isEqualTo(6);
    }
}