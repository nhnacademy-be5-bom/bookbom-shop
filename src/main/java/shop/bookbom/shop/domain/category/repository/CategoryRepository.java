package shop.bookbom.shop.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
