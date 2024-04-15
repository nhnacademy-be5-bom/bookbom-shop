package shop.bookbom.shop.domain.book.repository.custom;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;
import shop.bookbom.shop.domain.book.dto.response.BookDetailResponse;

/**
 * packageName    : shop.bookbom.shop.domain.book.repository.custom
 * fileName       : BookRepositoryCustom
 * author         : 전석준
 * date           : 2024-04-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-12        전석준       최초 생성
 */
@NoRepositoryBean
public interface BookRepositoryCustom {
    List<BookDetailResponse> getBookDetailById(Long bookId);

}
