package shop.bookbom.shop.domain.cart.service;


import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.repository.BookRepository;
import shop.bookbom.shop.domain.cart.dto.request.CartAddRequest;
import shop.bookbom.shop.domain.cart.entity.Cart;
import shop.bookbom.shop.domain.cartitem.entity.CartItem;
import shop.bookbom.shop.domain.cartitem.repository.CartItemRepository;
import shop.bookbom.shop.domain.member.entity.Member;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartFindService cartFindService;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;

    @Transactional
    public Cart addCart(List<CartAddRequest> addItems, Member member) {
        Cart cart = cartFindService.getCart(member);
        List<CartItem> cartItems = cart.getCartItems();
        addItems.forEach(c -> {
            Book book = bookRepository.findById(c.getBookId()).orElseThrow();
            int quantity = c.getQuantity();
            Optional<CartItem> cartItemOptional = cartItems.stream()
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
                cart.addItem(cartItem);
            }
        });
        return cart;
    }

    @Transactional
    public int updateQuantity(Long id, int quantity) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow();
        cartItem.addQuantity(quantity);
        return cartItem.getQuantity();
    }

    @Transactional
    public void deleteItem(Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }
}
