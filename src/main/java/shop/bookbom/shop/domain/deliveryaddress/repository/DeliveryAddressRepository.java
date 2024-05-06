package shop.bookbom.shop.domain.deliveryaddress.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.deliveryaddress.entity.DeliveryAddress;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {
}
