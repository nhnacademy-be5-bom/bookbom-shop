package shop.bookbom.shop.domain.member.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import shop.bookbom.shop.domain.rank.entity.Rank;
import shop.bookbom.shop.domain.role.entity.Role;
import shop.bookbom.shop.domain.users.entity.User;
import shop.bookbom.shop.domain.wish.entity.Wish;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
@SuperBuilder
public class Member extends User {

    @Column(length = 50, nullable = false)
    private String name;

    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(length = 30, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private int point;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberStatus status = MemberStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rank_id", nullable = false)
    private Rank rank;

    @OneToMany(mappedBy = "member")
    private List<Wish> wishList = new ArrayList<>();

    public Member(
            String email,
            String password,
            Role role,
            String name,
            String phoneNumber,
            LocalDate birthDate,
            String nickname,
            int point,
            MemberStatus status,
            Rank rank
    ) {
        super(email, password, role);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.nickname = nickname;
        this.point = point;
        this.status = status;
        this.rank = rank;
    }

    public void addWish(Wish wish) {
        wishList.add(wish);
    }

    public void deleteWish(Wish wish) {
        wishList.remove(wish);
    }
}
