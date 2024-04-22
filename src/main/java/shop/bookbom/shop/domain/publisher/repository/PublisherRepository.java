package shop.bookbom.shop.domain.publisher.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.publisher.entity.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Optional<Publisher> findByName(String name);
}
