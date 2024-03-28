package shop.bookbom.shop.address.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    private String nickName;

    @Column(name = "address_number")
    private String addressNumber;

    private String location;

    @Column(name = "default_address")
    private boolean defaultAddress;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Member member;

    public void editNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setDefaultAddress(boolean bool) {
        this.defaultAddress = defaultAddress;
    }

    @Builder
    public Address(String nickName,
                   String addressNumber,
                   String location,
                   boolean defaultAddress,
                   Long userId,
                   Member member) {
        this.nickName = nickName;
        this.addressNumber = addressNumber;
        this.location = location;
        this.defaultAddress = defaultAddress;
        this.userId = userId;
        this.member = member;
    }
}
