package shop.bookbom.shop.domain.bookcategory.repository;

import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.bookbom.shop.domain.bookcategory.entity.BookCategory;
import shop.bookbom.shop.domain.bookcategory.entity.QBookCategory;

public class BookCategoryRepositoryCustomImpl extends QuerydslRepositorySupport
        implements BookCategoryRepositoryCustom {
    private final QBookCategory bookCategory = QBookCategory.bookCategory;

    public BookCategoryRepositoryCustomImpl() {
        super(BookCategory.class);
    }

    @Override
    public List<Long> getCategoryIdByBookId(Long bookId) {
        return from(bookCategory)
                .select(bookCategory.category.id)
                .where(bookCategory.book.id.eq(bookId))
                .fetch();
    }

}
