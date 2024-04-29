package shop.bookbom.shop.domain.category.repository.custom;

import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;
import shop.bookbom.shop.domain.category.dto.response.CategoryNameAndChildResponse;

/**
 * packageName    : shop.bookbom.shop.domain.category.repository.custom
 * fileName       : CategoryRepositoryCustom
 * author         : UuLaptop
 * date           : 2024-04-25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-25        UuLaptop       최초 생성
 */
@NoRepositoryBean
public interface CategoryRepositoryCustom {

    CategoryNameAndChildResponse getNameAndChildrenById(Long categoryId);

    Optional<Boolean> hasBookLeftInsideOf(Long categoryId);
}
