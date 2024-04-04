package shop.bookbom.shop.domain.bookfile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.bookbom.shop.domain.bookfile.entity.BookFile;


@Repository
public interface BookFileRepository extends JpaRepository<BookFile, Long>, BookFileRepositoryCustom {

}
