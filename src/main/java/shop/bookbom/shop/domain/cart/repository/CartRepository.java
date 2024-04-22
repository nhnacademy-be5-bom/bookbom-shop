package shop.bookbom.shop.domain.cart.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.cart.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>, CartRepositoryCustom {
}
