package shop.bookbom.shop.domain.book.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.AggregationsContainer;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.TotalHitsRelation;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.suggest.response.Suggest;
import shop.bookbom.shop.domain.book.document.BookDocument;
import shop.bookbom.shop.domain.book.dto.BookSearchResponse;
import shop.bookbom.shop.domain.review.repository.ReviewRepository;

@ExtendWith(MockitoExtension.class)
class BookSearchServiceTest {

    @Mock
    ElasticsearchOperations operations;

    @Mock
    ReviewRepository reviewRepository;

    @InjectMocks
    BookSearchServiceImpl bookSearchService;

    private static SearchHits<BookDocument> getSearchHits(BookDocument bookDocument) {
        SearchHit<BookDocument> documentSearchHit =
                new SearchHit<>(null, null, null, 1.0f, null, null, null, null, null, null, bookDocument);
        return new SearchHits<>() {
            @Override
            public AggregationsContainer<?> getAggregations() {
                return null;
            }

            @Override
            public float getMaxScore() {
                return 0;
            }

            @Override
            public SearchHit<BookDocument> getSearchHit(int index) {
                return documentSearchHit;
            }

            @Override
            public List<SearchHit<BookDocument>> getSearchHits() {
                return List.of(documentSearchHit);
            }

            @Override
            public long getTotalHits() {
                return 1;
            }

            @Override
            public TotalHitsRelation getTotalHitsRelation() {
                return null;
            }

            @Override
            public Suggest getSuggest() {
                return null;
            }
        };
    }

    private static BookDocument getBookDocument() {
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

    @Test
    @DisplayName("도서 검색")
    void search() {
        // given
        PageRequest pageable = PageRequest.of(0, 5);
        String keyword = "title";
        String firstValue = "book_title";
        BookDocument bookDocument = getBookDocument();
        SearchHits<BookDocument> searchHits = getSearchHits(bookDocument);
        when(operations.search(any(Query.class), eq(BookDocument.class))).thenReturn(searchHits);
        when(reviewRepository.avgRateByBookId(anyLong())).thenReturn(Optional.of(4.5));
        when(reviewRepository.countByBookId(anyLong())).thenReturn(Optional.of(10L));
        // when
        Page<BookSearchResponse> result = bookSearchService.search(pageable, keyword, firstValue);
        // then
        List<BookSearchResponse> content = result.getContent();
        BookSearchResponse response = content.get(0);
        assertThat(content).hasSize(1);
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("title");
        assertThat(response.getReviewCount()).isEqualTo(10L);
        assertThat(response.getReviewRating()).isEqualTo(4.5);
    }
}
