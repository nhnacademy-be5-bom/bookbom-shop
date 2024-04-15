package shop.bookbom.shop.domain.book.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.NoRepositoryBean;
import shop.bookbom.shop.domain.book.dto.response.BookDetailResponse;
import shop.bookbom.shop.domain.book.dto.response.BookMediumResponse;
import shop.bookbom.shop.domain.book.dto.response.BookSimpleResponse;

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
     * description : 책 상세정보 조회에 사용
     *
     * @param book id
     * @return book detail response
     */
    BookDetailResponse getBookDetailInfoById(Long bookId);

    /**
     * methodName : getBookMediumInfoById
     * author : 전석준
     * description : 중간크기 DTO 단건조회
     *
     * @param book id
     * @return book medium response
     */
    BookMediumResponse getBookMediumInfoById(Long bookId);

    /**
     * methodName : getBookSimpleInfoById
     * author : 전석준
     * description : 주문, 장바구니에서 사용
     *
     * @param book id
     * @return book simple response
     */
    BookSimpleResponse getBookSimpleInfoById(Long bookId);

    /**
     * methodName : getPageableBookMediumInfoById
     * author : 전석준
     * description : 베스트 도서,도서 전체 목록에 사용
     *
     * @param book id
     * @return pageable List: 표지, 제목, 작가, 출판사, 출판일자, 포인트, 가격, 할인가격, 설명 , 별점, 리뷰갯수.
     */
    Page<BookMediumResponse> getPageableBookMediumInfoById(Long bookId);
}
