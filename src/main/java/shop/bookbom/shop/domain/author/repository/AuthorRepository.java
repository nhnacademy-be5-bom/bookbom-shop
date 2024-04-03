package shop.bookbom.shop.domain.author.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.author.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
