package shop.bookbom.shop.domain.deliveryaddress.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "delivery_address")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DeliveryAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_address_id", nullable = false)
    private Long id;

    @Column(name = "zip_code", nullable = false, length = 5)
    private String zipCode;

    @Column(name = "delivery_address", nullable = false, length = 200)
    private String deliveryAddress;

    @Column(name = "address_detail", nullable = false, length = 200)
    private String addressDetail;

    @Builder
    public DeliveryAddress(String zipCode, String deliveryAddress, String addressDetail) {
        this.zipCode = zipCode;
        this.deliveryAddress = deliveryAddress;
        this.addressDetail = addressDetail;
    }
}
