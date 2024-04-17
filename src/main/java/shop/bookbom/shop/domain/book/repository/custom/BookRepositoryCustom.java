package shop.bookbom.shop.domain.book.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.bookbom.shop.domain.book.dto.response.BookDetailResponse;
import shop.bookbom.shop.domain.book.dto.response.BookMediumResponse;
import shop.bookbom.shop.domain.book.dto.response.BookSimpleResponse;
import shop.bookbom.shop.domain.book.entity.Book;

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

    /**
     * methodName : getBookDetailInfoById
     * author : 전석준
     * description : 최대크기 DTO 단건조회
     * 책 상세정보 조회에 사용
     *
     * @param book id : 책id
     * @return 1 book detail response
     */
    BookDetailResponse getBookDetailInfoById(Long bookId);

    /**
     * methodName : getBookMediumInfoById
     * author : 전석준
     * description : 중간크기 DTO 단건조회
     *
     * @param book id 책id
     * @return 1 book medium response
     */
    BookMediumResponse getBookMediumInfoById(Long bookId);

    /**
     * methodName : getBookSimpleInfoById
     * author : 전석준
     * description : 최소크기 DTO 단건조회
     * 주문, 장바구니에서 사용
     *
     * @param book id 책id
     * @return 1 book simple response
     */
    BookSimpleResponse getBookSimpleInfoById(Long bookId);

    /**
     * methodName : findByIdFetch
     * author : 전석준
     * description : 업데이트를 위해 한권만 fetch join 으로 가져오기
     *
     * @param book id
     * @return book
     */
    Book findByIdFetch(Long bookId);

    /**
     * methodName : getPageableAndOrderByViewCountListBookMediumInfos
     * author : 전석준
     * description : 베스트 페이지에서 사용
     *
     * @param pageable 그거
     * @return page 조회순 정렬/pageable 된 도서 DTO
     */
    Page<BookMediumResponse> getPageableAndOrderByViewCountListBookMediumInfos(Pageable pageable);

    /**
     * methodName : getPageableListBookMediumInfos
     * author : 전석준
     * description : 전체책/관리자페이지에서 사용
     *
     * @param pageable 그거
     * @return page pageable 된 도서 DTO
     */
    Page<BookMediumResponse> getPageableListBookMediumInfos(Pageable pageable);

    /**
     * methodName : getPageableBookMediumInfosByCategoryId
     * author : 전석준
     * description : 카테고리별 책 조회에서 사용
     *
     * @param category id 카테고리번호
     * @param pageable 그거
     * @return page
     */
    Page<BookMediumResponse> getPageableBookMediumInfosByCategoryId(Long categoryId, Pageable pageable);
}