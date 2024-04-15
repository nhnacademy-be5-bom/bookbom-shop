package shop.bookbom.shop.domain.book.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static shop.bookbom.shop.domain.book.utils.BookTestUtils.getBookDocument;

import java.util.List;
import java.util.Optional;
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
import shop.bookbom.shop.domain.review.repository.ReviewRepository;

@ExtendWith(MockitoExtension.class)
class BookSearchServiceTest {

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    BookSearchRepository bookSearchRepository;

    @InjectMocks
    BookSearchServiceImpl bookSearchService;


    @Test
    @DisplayName("도서 검색 후 검색 결과 DTO 반환")
    void search() {
        // given
        PageRequest pageable = PageRequest.of(0, 5);
        String keyword = "title";
        String firstValue = "book_title";
        BookDocument bookDocument = getBookDocument();
        List<BookDocument> searchResult = List.of(bookDocument);
        PageImpl<BookDocument> searchResponse = new PageImpl<>(searchResult, pageable, searchResult.size());
        when(bookSearchRepository.search(any(), any(), any())).thenReturn(searchResponse);
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
