package shop.bookbom.shop.domain.cart.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.bookbom.shop.domain.cart.entity.Cart;
import shop.bookbom.shop.domain.member.entity.Member;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("select c from Cart c join fetch c.cartItems ci where c.member = :member")
    Optional<Cart> getCartFetchItems(Member member);
}
