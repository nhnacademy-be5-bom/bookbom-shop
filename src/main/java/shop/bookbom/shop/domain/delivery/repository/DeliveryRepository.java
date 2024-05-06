package shop.bookbom.shop.domain.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.delivery.entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    
}
