package shop.bookbom.shop.domain.book.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static shop.bookbom.shop.domain.book.utils.BookTestUtils.getBookDocument;
import static shop.bookbom.shop.domain.book.utils.BookTestUtils.getSearchHits;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitsImpl;
import org.springframework.data.elasticsearch.core.query.Query;
import shop.bookbom.shop.domain.book.document.BookDocument;
import shop.bookbom.shop.domain.book.repository.impl.BookSearchRepositoryImpl;

@ExtendWith(MockitoExtension.class)
class BookSearchRepositoryTest {

    @Mock
    ElasticsearchOperations operations;

    @InjectMocks
    BookSearchRepositoryImpl bookSearchRepository;

    @Test
    void search() {
        PageRequest pageable = PageRequest.of(0, 5);
        String keyword = "title";
        String firstValue = "book_title";
        BookDocument bookDocument = getBookDocument();
        SearchHitsImpl<BookDocument> searchHits = getSearchHits(bookDocument);
        when(operations.search(any(Query.class), eq(BookDocument.class))).thenReturn(searchHits);
        Page<BookDocument> search = bookSearchRepository.search(pageable, keyword, firstValue);
        BookDocument result = search.getContent().get(0);
        assertThat(result.getBookId()).isEqualTo(1L);
        assertThat(result.getBookTitle()).isEqualTo("title");
    }
}
