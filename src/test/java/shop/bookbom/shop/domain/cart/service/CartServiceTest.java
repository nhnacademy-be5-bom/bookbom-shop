package shop.bookbom.shop.domain.cart.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static shop.bookbom.shop.domain.cart.service.CartTestUtils.getBook;
import static shop.bookbom.shop.domain.cart.service.CartTestUtils.getCart;
import static shop.bookbom.shop.domain.cart.service.CartTestUtils.getCartAddRequest;
import static shop.bookbom.shop.domain.cart.service.CartTestUtils.getMember;

import java.util.List;
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
import shop.bookbom.shop.domain.cart.dto.repsonse.CartInfoResponse;
import shop.bookbom.shop.domain.cart.dto.repsonse.CartUpdateResponse;
import shop.bookbom.shop.domain.cart.entity.Cart;
import shop.bookbom.shop.domain.cart.service.impl.CartServiceImpl;
import shop.bookbom.shop.domain.cartitem.entity.CartItem;
import shop.bookbom.shop.domain.cartitem.exception.CartItemInvalidQuantityException;
import shop.bookbom.shop.domain.cartitem.exception.CartItemNotFoundException;
import shop.bookbom.shop.domain.cartitem.repository.CartItemRepository;
import shop.bookbom.shop.domain.member.entity.Member;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    CartServiceImpl cartService;

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
        Book book1 = getBook(1L, "title1");
        Book book2 = getBook(2L, "title2");
        Book book3 = getBook(3L, "title3");
        Member member = getMember(1L, "test@email.com");
        Cart cart = getCart(member, 1L);
        when(cartFindService.getCart(any())).thenReturn(cart);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        when(bookRepository.findById(2L)).thenReturn(Optional.of(book2));
        when(bookRepository.findById(3L)).thenReturn(Optional.of(book3));

        // when
        CartInfoResponse cartInfo = cartService.addCart(getCartAddRequest(), member.getId());

        //then
        verify(cartItemRepository, times(3)).save(any());
        List<CartInfoResponse.CartItemInfo> cartItems = cartInfo.getCartItems();
        assertThat(cartInfo.getCartId()).isEqualTo(cart.getId());
        assertThat(cartItems).hasSize(3);
        assertThat(cartItems.get(0).getBookId()).isEqualTo(1L);
        assertThat(cartItems.get(0).getQuantity()).isEqualTo(1);
    }

    @Test
    @DisplayName("장바구니에 있던 도서 추가")
    void addCartExists() {
        // given
        Book book1 = getBook(1L, "title1");
        Book book2 = getBook(2L, "title2");
        Book book3 = getBook(3L, "title3");
        Member member = getMember(1L, "test@email.com");
        Cart cart = getCart(member, 1L);
        cart.addItem(CartItem.builder()
                .cart(cart)
                .book(book1)
                .quantity(1)
                .build());
        cart.addItem(CartItem.builder()
                .cart(cart)
                .book(book2)
                .quantity(1)
                .build());
        cart.addItem(CartItem.builder()
                .cart(cart)
                .book(book3)
                .quantity(1)
                .build());
        when(cartFindService.getCart(any())).thenReturn(cart);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        when(bookRepository.findById(2L)).thenReturn(Optional.of(book2));
        when(bookRepository.findById(3L)).thenReturn(Optional.of(book3));

        // when
        CartInfoResponse cartInfo = cartService.addCart(getCartAddRequest(), member.getId());

        //then
        verify(cartItemRepository, times(0)).save(any());
        List<CartInfoResponse.CartItemInfo> cartItems = cartInfo.getCartItems();
        assertThat(cartInfo.getCartId()).isEqualTo(cart.getId());
        assertThat(cartItems).hasSize(3);
        assertThat(cartItems.get(0).getBookId()).isEqualTo(1L);
        assertThat(cartItems.get(0).getQuantity()).isEqualTo(1 + 1);
    }


    @Test
    @DisplayName("장바구니 목록 중 삭제")
    void delete() throws Exception {
        //given
        CartItem cartItem = CartItem.builder()
                .cart(getCart(getMember(1L, "test@email.com"), 1L))
                .book(getBook(1L, "title1"))
                .build();
        ReflectionTestUtils.setField(cartItem, "id", 1L);
        when(cartItemRepository.findById(1L)).thenReturn(Optional.of(cartItem));

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
                .cart(getCart(getMember(1L, "test@email.com"), 1L))
                .book(getBook(1L, "title1"))
                .build();
        ReflectionTestUtils.setField(cartItem, "id", 1L);
        when(cartItemRepository.findById(1L)).thenReturn(Optional.of(cartItem));

        //when
        CartUpdateResponse response = cartService.updateQuantity(1L, 20);

        //then
        assertThat(response.getQuantity()).isEqualTo(20);
    }

    @Test
    @DisplayName("장바구니 도서 수량 수정 예외")
    void updateQuantityException() throws Exception {
        // given
        CartItem cartItem = CartItem.builder()
                .cart(getCart(getMember(1L, "test@email.com"), 1L))
                .book(getBook(1L, "title1"))
                .build();
        ReflectionTestUtils.setField(cartItem, "id", 1L);
        when(cartItemRepository.findById(1L)).thenReturn(Optional.of(cartItem));

        // when & then
        assertThatThrownBy(() -> cartService.updateQuantity(1L, 0))
                .isInstanceOf(CartItemInvalidQuantityException.class);
    }

    @Test
    @DisplayName("장바구니 정보 조회")
    void getCartInfo() throws Exception {
        //given
        Cart cart = getCart(getMember(1L, "test@email.com"), 1L);
        Book book = getBook(1L, "title1");
        cart.addItem(CartItem.builder()
                .cart(cart)
                .book(book)
                .quantity(3)
                .build());
        when(cartFindService.getCart(1L)).thenReturn(cart);

        //when
        CartInfoResponse cartInfo = cartService.getCartInfo(1L);

        //then
        List<CartInfoResponse.CartItemInfo> cartItems = cartInfo.getCartItems();
        assertThat(cartInfo.getCartId()).isEqualTo(1L);
        assertThat(cartItems).hasSize(1);
        assertThat(cartItems.get(0).getBookId()).isEqualTo(1L);
        assertThat(cartItems.get(0).getQuantity()).isEqualTo(3);
    }
}