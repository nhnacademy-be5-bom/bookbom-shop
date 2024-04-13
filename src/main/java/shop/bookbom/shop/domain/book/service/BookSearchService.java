package shop.bookbom.shop.domain.book.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.bookbom.shop.domain.book.dto.BookSearchResponse;

public interface BookSearchService {

    /**
     * 키워드를 가지고 도서를 검색하는 메서드입니다.
     *
     * @param pageable   페이지 정보
     * @param keyword    검색 키워드
     * @param firstValue 검색 조건
     * @return 검색 결과
     */
    Page<BookSearchResponse> search(Pageable pageable, String keyword, String firstValue);

    /**
     * 책 ID에 해당하는 리뷰 개수를 반환합니다.
     * // todo bookService로 이동
     *
     * @param bookId
     * @return 리뷰 개수
     */
    long getReviewCount(Long bookId);

    /**
     * 책 ID에 해당하는 리뷰 평균 평점을 반환합니다.
     * // todo bookService로 이동
     *
     * @param bookId
     * @return 리뷰 평균 평점
     */
    double getReviewRating(Long bookId);
}
