package shop.bookbom.shop.domain.category.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.bookbom.shop.domain.category.entity.Category;
import shop.bookbom.shop.domain.category.entity.Status;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    List<Category> findAllByStatus(Status status);

    @Query("select c from Category c join fetch c.child where c.parent=null and c.status = :status")
    List<Category> findAllByStatusAndParentNull(@Param("status") Status status);

    List<Category> findAllByStatusAndParent_Id(Status status, Long parentId);
}
