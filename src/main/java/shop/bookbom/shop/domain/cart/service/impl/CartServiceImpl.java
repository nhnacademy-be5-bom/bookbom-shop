package shop.bookbom.shop.domain.cart.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.exception.BookNotFoundException;
import shop.bookbom.shop.domain.book.repository.BookRepository;
import shop.bookbom.shop.domain.cart.dto.repsonse.CartInfoResponse;
import shop.bookbom.shop.domain.cart.dto.repsonse.CartUpdateResponse;
import shop.bookbom.shop.domain.cart.dto.request.CartAddRequest;
import shop.bookbom.shop.domain.cart.entity.Cart;
import shop.bookbom.shop.domain.cart.service.CartFindService;
import shop.bookbom.shop.domain.cart.service.CartService;
import shop.bookbom.shop.domain.cartitem.entity.CartItem;
import shop.bookbom.shop.domain.cartitem.exception.CartItemNotFoundException;
import shop.bookbom.shop.domain.cartitem.repository.CartItemRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {
    private final CartFindService cartFindService;
    private final BookRepository bookRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public CartInfoResponse addCart(List<CartAddRequest> addItems, Long userId) {
        Cart cart = cartFindService.getCart(userId);
        addItems.forEach(c -> {
            Book book = bookRepository.findById(c.getBookId())
                    .orElseThrow(BookNotFoundException::new);
            int quantity = c.getQuantity();
            Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                    .filter(cartitem -> cartitem.getBook().equals(book))
                    .findFirst();
            if (cartItemOptional.isPresent()) {
                cartItemOptional.get().addQuantity(quantity);
            } else {
                CartItem cartItem = CartItem.builder()
                        .book(book)
                        .cart(cart)
                        .quantity(quantity)
                        .build();
                cartItemRepository.save(cartItem);
                cart.addItem(cartItem);
            }
        });
        return cartToCartInfoResponse(cart);
    }

    @Override
    @Transactional
    public CartInfoResponse getCartInfo(Long userId) {
        Cart cart = cartFindService.getCart(userId);
        return cartToCartInfoResponse(cart);
    }

    @Override
    @Transactional
    public CartUpdateResponse updateQuantity(Long id, int quantity) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(CartItemNotFoundException::new);
        cartItem.updateQuantity(quantity);
        return CartUpdateResponse.builder()
                .quantity(cartItem.getQuantity())
                .build();
    }

    @Override
    @Transactional
    public void deleteItem(Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(CartItemNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }

    private CartInfoResponse cartToCartInfoResponse(Cart cart) {
        List<CartInfoResponse.CartItemInfo> cartItems = cart.getCartItems().stream()
                .map(ci -> CartInfoResponse.CartItemInfo.builder()
                        .bookId(ci.getBook().getId())
                        .quantity(ci.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return CartInfoResponse.builder()
                .cartId(cart.getId())
                .cartItems(cartItems)
                .build();
    }
}
