package shop.bookbom.shop.domain.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.role.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
