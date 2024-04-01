package shop.bookbom.shop.domain.cartitem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.cartitem.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
