package shop.bookbom.shop.domain.category.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.bookbom.shop.domain.category.dto.CategoryDTO;
import shop.bookbom.shop.domain.category.entity.Category;
import shop.bookbom.shop.domain.category.entity.Status;
import shop.bookbom.shop.domain.category.repository.custom.CategoryRepositoryCustom;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {

    Optional<Category> findByName(String name);

    List<Category> findAllByStatus(Status status);

    @Query(value =
            "select new shop.bookbom.shop.domain.category.dto.CategoryDTO(c.id, c.name)" +
                    "from Category c where c.parent=null and c.status = shop.bookbom.shop.domain.category.entity.Status.USED")
    List<CategoryDTO> findAllAtDepthOne();

    @Query(value =
            "select new shop.bookbom.shop.domain.category.dto.CategoryDTO(c.id, c.name)" +
                    "from Category c " +
                    "where c.parent.id=:parentId and c.status = shop.bookbom.shop.domain.category.entity.Status.USED")
    List<CategoryDTO> findAllByParentId(@Param("parentId") Long parentId);
}
