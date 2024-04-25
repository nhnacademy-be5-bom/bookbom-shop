package shop.bookbom.shop.domain.file.repository.custom;

import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;
import shop.bookbom.shop.domain.file.entity.File;

/**
 * packageName    : shop.bookbom.shop.domain.file.repository.custom
 * fileName       : FileRepositoryCustom
 * author         : UuLaptop
 * date           : 2024-04-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-22        UuLaptop       최초 생성
 */
@NoRepositoryBean
public interface FileRepositoryCustom {
    Optional<File> findThumbnailByBookId(Long bookId);
}
