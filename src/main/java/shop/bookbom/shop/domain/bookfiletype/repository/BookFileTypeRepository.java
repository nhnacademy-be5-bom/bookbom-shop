package shop.bookbom.shop.domain.bookfiletype.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.bookfiletype.entity.BookFileType;

/**
 * packageName    : shop.bookbom.shop.domain.bookfiletype.repository
 * fileName       : BookFileTypeRepository
 * author         : UuLaptop
 * date           : 2024-04-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-22        UuLaptop       최초 생성
 */
public interface BookFileTypeRepository extends JpaRepository<BookFileType, Long> {
}
