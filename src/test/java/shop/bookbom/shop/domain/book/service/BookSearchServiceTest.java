package shop.bookbom.shop.domain.book.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static shop.bookbom.shop.domain.book.utils.BookTestUtils.getBookDocument;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import shop.bookbom.shop.domain.book.document.BookDocument;
import shop.bookbom.shop.domain.book.dto.BookSearchResponse;
import shop.bookbom.shop.domain.book.repository.BookSearchRepository;
import shop.bookbom.shop.domain.book.service.impl.BookSearchServiceImpl;

@ExtendWith(MockitoExtension.class)
class BookSearchServiceTest {

    @Mock
    BookSearchRepository bookSearchRepository;

    @InjectMocks
    BookSearchServiceImpl bookSearchService;

    @Mock
    BookService bookService;


    @Test
    @DisplayName("도서 검색 후 검색 결과 DTO 반환")
    void search() {
        // given
        PageRequest pageable = PageRequest.of(0, 5);
        String keyword = "title";
        String searchCond = "book_title";
        String sortCond = "none";
        BookDocument bookDocument = getBookDocument();
        List<BookDocument> searchResult = List.of(bookDocument);
        PageImpl<BookDocument> searchResponse = new PageImpl<>(searchResult, pageable, searchResult.size());
        when(bookSearchRepository.search(any(), any(), any(), any())).thenReturn(searchResponse);
        when(bookService.getReviewCount(anyLong())).thenReturn(10L);
        when(bookService.getReviewRating(anyLong())).thenReturn(4.5);
        // when
        Page<BookSearchResponse> result = bookSearchService.search(pageable, keyword, searchCond, sortCond);
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
