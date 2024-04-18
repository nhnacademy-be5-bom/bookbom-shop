package shop.bookbom.shop.domain.bookfile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.bookfile.entity.BookFile;


public interface BookFileRepository extends JpaRepository<BookFile, Long>, BookFileRepositoryCustom {

}
