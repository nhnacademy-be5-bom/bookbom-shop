package shop.bookbom.shop.domain.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.tag.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsByName(String name);
}
