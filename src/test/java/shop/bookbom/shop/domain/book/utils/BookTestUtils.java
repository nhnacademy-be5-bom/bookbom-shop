package shop.bookbom.shop.domain.book.utils;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHitsImpl;
import org.springframework.data.elasticsearch.core.TotalHitsRelation;
import shop.bookbom.shop.domain.author.dto.AuthorResponse;
import shop.bookbom.shop.domain.book.document.BookDocument;
import shop.bookbom.shop.domain.book.dto.BookSearchResponse;

public class BookTestUtils {
    private BookTestUtils() {

    }

    public static BookDocument getBookDocument() {
        return new BookDocument(
                1L,
                "title",
                "description",
                "index",
                10000,
                9000,
                LocalDate.now(),
                "1",
                "role",
                "name",
                1L,
                "publisher",
                0,
                1L,
                "thumbnail",
                "jpg");
    }


    public static SearchHitsImpl<BookDocument> getSearchHits(BookDocument bookDocument) {
        SearchHit<BookDocument> searchHit = new SearchHit<>(
                "book_index", // 인덱스 이름
                "1", // 문서 ID
                null, // 라우팅 값
                1.0f, // 점수
                null, // 정렬 값
                null, // 하이라이트 필드
                null, // 내부 히트
                null, // 중첩 메타데이터
                null, // 설명
                null, // 일치하는 쿼리
                bookDocument // 내용
        );
        return new SearchHitsImpl<>(
                1L, // 총 히트 수
                TotalHitsRelation.EQUAL_TO, // 히트 수 관계
                1.0f, // 최대 점수
                null, // 스크롤 ID
                List.of(searchHit), // 검색 히트 리스트
                null, // 집계 정보
                null  // 제안 정보
        );
    }

    public static BookSearchResponse getBookSearchResponse() {
        return new BookSearchResponse(
                1L,
                "thumbnail",
                "title",
                List.of(new AuthorResponse(1L, "name", "지은이")),
                1L,
                "publisher",
                LocalDate.now(),
                10000,
                8000,
                4.5,
                10L
        );
    }
}
