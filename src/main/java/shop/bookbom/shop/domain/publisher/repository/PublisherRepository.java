package shop.bookbom.shop.domain.publisher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.publisher.entity.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
