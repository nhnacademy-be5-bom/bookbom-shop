package shop.bookbom.shop.domain.cartitem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static shop.bookbom.shop.domain.cart.service.CartTestUtils.getBook;
import static shop.bookbom.shop.domain.cart.service.CartTestUtils.getCart;
import static shop.bookbom.shop.domain.cart.service.CartTestUtils.getMember;

import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.bookbom.shop.domain.cartitem.entity.CartItem;
import shop.bookbom.shop.domain.cartitem.repository.CartItemRepository;

@ExtendWith(MockitoExtension.class)
class CartItemServiceTest {

    @InjectMocks
    CartItemService cartItemService;

    @Mock
    CartItemRepository cartItemRepository;

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
        cartItemService.deleteItem(1L);

        //then
        verify(cartItemRepository).delete(cartItem);
    }

    @Test
    @DisplayName("장바구니 목록 중 삭제 예외")
    void deleteNotExistsCartItem() throws Exception {
        //given
        when(cartItemRepository.findById(any())).thenReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> cartItemService.deleteItem(1L))
                .isInstanceOf(EntityNotFoundException.class);
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
        int quantity = cartItemService.updateQuantity(1L, 3);

        //then
        assertThat(quantity).isEqualTo(6);
    }
}