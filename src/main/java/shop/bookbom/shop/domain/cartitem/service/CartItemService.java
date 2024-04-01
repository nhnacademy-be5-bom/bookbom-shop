package shop.bookbom.shop.domain.cartitem.service;

import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.cartitem.entity.CartItem;
import shop.bookbom.shop.domain.cartitem.repository.CartItemRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    @Transactional
    public void deleteItem(Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }

    @Transactional
    public int updateQuantity(Long id, int quantity) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        cartItem.addQuantity(quantity);
        return cartItem.getQuantity();
    }
}
