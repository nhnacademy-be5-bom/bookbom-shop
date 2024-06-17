package shop.bookbom.shop.domain.bookcategory.repository;

import java.util.List;

public interface BookCategoryRepositoryCustom {
    List<Long> getCategoryIdByBookId(Long bookId);
}
