package shop.bookbom.shop.cart.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.users.entity.Users;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "user_id")
    private Long userId;

    @OneToOne(mappedBy = "cart")
    private Users user;

    @Builder
    public Cart(Long userId, Users user) {
        this.userId = userId;
        this.user = user;
    }
}
