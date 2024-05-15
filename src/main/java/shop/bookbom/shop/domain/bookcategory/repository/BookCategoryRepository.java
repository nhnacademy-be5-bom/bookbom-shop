package shop.bookbom.shop.domain.bookcategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.bookcategory.entity.BookCategory;

public interface BookCategoryRepository extends JpaRepository<BookCategory, Long>, BookCategoryRepositoryCustom {


}
