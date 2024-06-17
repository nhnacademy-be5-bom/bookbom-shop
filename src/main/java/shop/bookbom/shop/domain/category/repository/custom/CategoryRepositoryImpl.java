package shop.bookbom.shop.domain.category.repository.custom;

import static shop.bookbom.shop.domain.bookcategory.entity.QBookCategory.bookCategory;
import static shop.bookbom.shop.domain.category.entity.QCategory.category;

import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import shop.bookbom.shop.domain.category.dto.response.CategoryNameAndChildResponse;
import shop.bookbom.shop.domain.category.entity.Category;
import shop.bookbom.shop.domain.category.entity.Status;

/**
 * packageName    : shop.bookbom.shop.domain.category.repository.custom
 * fileName       : CategoryRepositoryCustomImpl
 * author         : UuLaptop
 * date           : 2024-04-25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-25        UuLaptop       최초 생성
 */
@Repository
public class CategoryRepositoryImpl extends QuerydslRepositorySupport implements CategoryRepositoryCustom {
    public CategoryRepositoryImpl() {
        super(Category.class);
    }

    @Override
    public CategoryNameAndChildResponse getNameAndChildrenById(Long categoryId) {

        Category categoryEntity = from(category)
                .where(category.id.eq(categoryId).and(category.status.ne(Status.DEL)))
                .select(category)
                .fetchOne();

        return CategoryNameAndChildResponse.from(categoryEntity);
    }

    @Override
    public Optional<Boolean> isBookInside(Long categoryId) {
        long bookCount = from(bookCategory)
                .where(bookCategory.category.id.eq(categoryId))
                .select(bookCategory.book.count())
                .fetchOne();

        return Optional.of(bookCount > 0);
    }
}
