package shop.bookbom.shop.domain.users.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.bookbom.shop.domain.users.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId);

    boolean existsUserByEmail(String email);
}
