package shop.bookbom.shop.users.entity;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import shop.bookbom.shop.cart.entity.Cart;
import shop.bookbom.shop.wish.entity.Wish;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String password;

    private Boolean registered;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToMany(mappedBy = "member")
    private List<Wish> wishList = new ArrayList<>();

    public User(String email,
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
