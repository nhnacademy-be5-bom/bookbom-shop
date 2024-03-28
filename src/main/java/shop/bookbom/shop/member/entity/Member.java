package shop.bookbom.shop.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import shop.bookbom.shop.cart.entity.Cart;
import shop.bookbom.shop.rank.entity.Rank;
import shop.bookbom.shop.users.entity.Users;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
@SuperBuilder
public class Member extends Users {

    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String nickname;

    private int point;

    private MemberStatus status;

    @Column(name = "rank_id")
    private Long rankId;

    @Column(name = "role_id")
    private Long roleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rank_id", insertable = false, updatable = false)
    private Rank rank;
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
                  Long rankId,
                  Long roleId,
                  Rank rank) {
        super(email, password, registered, cart);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.nickname = nickname;
        this.point = point;
        this.status = status;
        this.rankId = rankId;
        this.roleId = roleId;
        this.rank = rank;
    }
}
