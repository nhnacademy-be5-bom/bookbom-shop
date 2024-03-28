package shop.bookbom.shop.member.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import shop.bookbom.shop.cart.entity.Cart;
import shop.bookbom.shop.rank.entity.Rank;
import shop.bookbom.shop.role.entity.Role;
import shop.bookbom.shop.users.entity.User;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
@SuperBuilder
public class Member extends User {

    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String nickname;

    private int point;

    private MemberStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rank_id")
    private Rank rank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    public Member(String email,
                  String password,
                  Boolean registered,
                  Cart cart,
                  String name,
                  String phoneNumber,
                  LocalDate birthDate,
                  String nickname,
                  int point,
                  MemberStatus status,
                  Rank rank,
                  Role role) {
        super(email, password, registered, cart);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.nickname = nickname;
        this.point = point;
        this.status = status;
        this.rank = rank;
        this.role = role;
    }
}
