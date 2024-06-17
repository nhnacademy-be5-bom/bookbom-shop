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
import shop.bookbom.shop.domain.bookfile.repository.BookFileRepository;
import shop.bookbom.shop.domain.cart.dto.repsonse.CartInfoResponse;
import shop.bookbom.shop.domain.cart.dto.repsonse.CartItemDto;
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
public class CartServiceImpl implements CartService {
    private final CartFindService cartFindService;
    private final BookRepository bookRepository;
    private final CartItemRepository cartItemRepository;
    private final BookFileRepository bookFileRepository;

    @Override
    @Transactional
    public List<Long> addCart(List<CartAddRequest> addItems, Long userId) {
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
        return cart.getCartItems().stream()
                .map(CartItem::getId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CartInfoResponse getCartInfo(Long userId) {
        Cart cart = cartFindService.getCart(userId);
        List<CartItemDto> cartItems = cart.getCartItems().stream()
                .map(cartItem -> {
                    String thumbnail = bookFileRepository.getBookImageUrl(cartItem.getBook().getId());
                    return CartItemDto.from(cartItem, thumbnail);
                })
                .collect(Collectors.toList());
        return CartInfoResponse.of(cart.getId(), cartItems);
    }

    @Override
    @Transactional
    public CartUpdateResponse updateQuantity(Long userId, Long bookId, int quantity) {
        Cart cart = cartFindService.getCart(userId);
        CartItem cartItem = cart.getCartItems().stream()
                .filter(ci -> ci.getBook().getId().equals(bookId))
                .findFirst()
                .orElseThrow(CartItemNotFoundException::new);
        cartItem.updateQuantity(quantity);
        return CartUpdateResponse.builder()
                .quantity(cartItem.getQuantity())
                .build();
    }

    @Override
    @Transactional
    public void deleteItem(Long userId, Long bookId) {
        Cart cart = cartFindService.getCart(userId);
        CartItem cartItem = cart.getCartItems().stream()
                .filter(ci -> ci.getBook().getId().equals(bookId))
                .findFirst()
                .orElseThrow(CartItemNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }
}
