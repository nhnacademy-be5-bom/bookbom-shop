package shop.bookbom.shop.domain.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.address.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
