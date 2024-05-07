package shop.bookbom.shop.domain.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {


}
