package shop.bookbom.shop.domain.cart.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.bookbom.shop.domain.cart.entity.Cart;
import shop.bookbom.shop.domain.member.entity.Member;

public interface CartRepository extends JpaRepository<Cart, Long> {
    /**
     * 장바구니와 장바구니 상품을 한번에 가져오는 메서드입니다.
     *
     * @param member 회원
     * @return
     */
    @Query("select c from Cart c left outer join fetch c.cartItems ci where c.member = :member")
    Optional<Cart> getCartFetchItems(Member member);
}
