package shop.bookbom.shop.domain.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.file.entity.File;
import shop.bookbom.shop.domain.file.repository.custom.FileRepositoryCustom;

/**
 * packageName    : shop.bookbom.shop.domain.file.repository
 * fileName       : FileRepository
 * author         : UuLaptop
 * date           : 2024-04-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-22        UuLaptop       최초 생성
 */
public interface FileRepository extends JpaRepository<File, Long>, FileRepositoryCustom {
}
