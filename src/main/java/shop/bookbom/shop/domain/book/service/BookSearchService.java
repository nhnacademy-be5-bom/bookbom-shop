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
     * @param searchCond 검색 조건
     * @param sortCond   정렬 조건
     * @return 검색 결과
     */
    Page<BookSearchResponse> search(Pageable pageable, String keyword, String searchCond, String sortCond);
}
