package shop.bookbom.shop.domain.paymentmethod.entity;

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
@Table(name = "payment_method")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(name = "card_company_code", nullable = true, length = 2)
    private String cardCompanyCode;

    @Builder
    public PaymentMethod(String name, String cardCompanyCode) {
        this.name = name;
        this.cardCompanyCode = cardCompanyCode;
    }
}
