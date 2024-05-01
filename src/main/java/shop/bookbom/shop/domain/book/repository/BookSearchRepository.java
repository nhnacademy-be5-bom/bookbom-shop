package shop.bookbom.shop.domain.book.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.bookbom.shop.domain.book.document.BookDocument;
import shop.bookbom.shop.domain.book.dto.SearchCondition;
import shop.bookbom.shop.domain.book.dto.SortCondition;

public interface BookSearchRepository {
    /**
     * 키워드와 우선 검색 조건을 입력받아 Elasticsearch로 검색하는 메서드입니다.
     *
     * @param pageable   페이지 정보
     * @param keyword    검색 키워드
     * @param searchCond 검색 조건
     * @param sortCond   정렬 조건
     */
    Page<BookDocument> search(Pageable pageable, String keyword, SearchCondition searchCond, SortCondition sortCond);
}
