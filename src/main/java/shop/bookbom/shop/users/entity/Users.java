package shop.bookbom.shop.users.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import shop.bookbom.shop.cart.entity.Cart;
import shop.bookbom.shop.wish.entity.Wish;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String email;

    private String password;

    private Boolean registered;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToMany(mappedBy = "member")
    private List<Wish> wishList = new ArrayList<>();

    public Users(String email,
                 String password,
                 Boolean registered,
                 Cart cart) {
        this.email = email;
        this.password = password;
        this.registered = registered;
        this.cart = cart;
    }

    public void addWish(Wish wish) {
        wishList.add(wish);
    }

    public void deleteWish(Wish wish) {
        wishList.remove(wish);
    }
}
