package shop.bookbom.shop.domain.deletereasoncategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.deletereasoncategory.entity.DeleteReasonCategory;

public interface DeleteReasonCategoryRepository extends JpaRepository<DeleteReasonCategory, Long> {
    DeleteReasonCategory findByName(String name);
}
