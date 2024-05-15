package shop.bookbom.shop.domain.address.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.address.entity.Address;
import shop.bookbom.shop.domain.member.entity.Member;

public interface AddressRepository extends JpaRepository<Address, Long>, AddressRepositoryCustom {
    List<Address> findByMember(Member member);

    long countByMember(Member member);
}
