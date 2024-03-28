package shop.bookbom.shop.tag.repository;

import shop.bookbom.shop.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
