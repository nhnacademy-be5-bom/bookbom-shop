package shop.bookbom.shop.domain.paymentmethod.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.paymentmethod.entity.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    Optional<PaymentMethod> findByName(String name);

    Optional<PaymentMethod> findByCardCompanyCode(String cardCompanyCode);
}
