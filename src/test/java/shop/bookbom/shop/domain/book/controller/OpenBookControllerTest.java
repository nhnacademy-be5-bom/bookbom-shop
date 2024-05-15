package shop.bookbom.shop.domain.book.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static shop.bookbom.shop.domain.book.utils.BookTestUtils.getBookDetailResponse;
import static shop.bookbom.shop.domain.book.utils.BookTestUtils.getBookMediumResponse;
import static shop.bookbom.shop.domain.book.utils.BookTestUtils.getBookSearchResponse;
import static shop.bookbom.shop.domain.book.utils.BookTestUtils.getBookSimpleResponse;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shop.bookbom.shop.domain.book.dto.BookSearchResponse;
import shop.bookbom.shop.domain.book.dto.response.BookDetailResponse;
import shop.bookbom.shop.domain.book.dto.response.BookMediumResponse;
import shop.bookbom.shop.domain.book.dto.response.BookSimpleResponse;
import shop.bookbom.shop.domain.book.service.BookSearchService;
import shop.bookbom.shop.domain.book.service.BookService;

/**
 * packageName    : shop.bookbom.shop.domain.book.controller
 * fileName       : GetSingleBookRestControllerTest
 * author         : UuLaptop
 * date           : 2024-04-17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-17        UuLaptop       최초 생성
 */
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(OpenBookController.class)
class OpenBookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService bookService;

    @MockBean
    BookSearchService bookSearchService;

    List<BookSearchResponse> result = new ArrayList<>();

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 20; i++) {
            result.add(getBookSearchResponse());
        }
    }

    @Test
    @DisplayName("도서 검색 요청")
    void search() throws Exception {
        //given
        String keyword = "book_title";
        String sortCond = "none";
        String searchCond = "none";
        PageRequest pageRequest = PageRequest.of(0, 5);
        when(bookSearchService.search(any(), any(), any(), any())).thenReturn(
                new PageImpl<>(List.of(getBookSearchResponse()), pageRequest, 1));
        //when
        ResultActions perform = mockMvc.perform(get("/shop/open/search")
                .param("keyword", keyword)
                .param("searchCond", searchCond)
                .param("sortCond", sortCond));
        //then
        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true))
                .andExpect(jsonPath("$.result.content.size()").value(1));
    }

    @Test
    @DisplayName("1건 조회: 책 상세정보")
    void getBookDetail() throws Exception {
        BookDetailResponse detailResponse = getBookDetailResponse(1L, "제목");

        when(bookService.getBookDetailInformation(1L)).thenReturn(detailResponse);

        ResultActions perform = mockMvc.perform(get("/shop/open/books/detail/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        verify(bookService, times(1)).getBookDetailInformation(anyLong());

        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true))
                .andExpect(jsonPath("$.result.id").value(1))
                .andExpect(jsonPath("$.result.title").value("제목"))
                .andExpect(jsonPath("$.result.publisher.name").value("출판사1"))
                .andExpect(jsonPath("$.result.authors.length()").value(2))
                .andExpect(jsonPath("$.result.tags.length()").value(3))
                .andExpect(jsonPath("$.result.categories.length()").value(2))
                .andExpect(jsonPath("$.result.files[0].url").value("img_url"));
    }

    @Test
    @DisplayName("1건 조회: 책 중간크기 정보")
    void getBookMedium() throws Exception {
        BookMediumResponse mediumResponse = getBookMediumResponse(1L, "제목");

        when(bookService.getBookMediumInformation(1L)).thenReturn(mediumResponse);

        ResultActions perform = mockMvc.perform(get("/shop/open/books/medium/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        verify(bookService, times(1)).getBookMediumInformation(anyLong());

        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true))
                .andExpect(jsonPath("$.result.id").value(1))
                .andExpect(jsonPath("$.result.title").value("제목"))
                .andExpect(jsonPath("$.result.publisher.name").value("출판사1"))
                .andExpect(jsonPath("$.result.authors.length()").value(2))
                .andExpect(jsonPath("$.result.tags.length()").value(3))
                .andExpect(jsonPath("$.result.reviews.length()").value(3))
                .andExpect(jsonPath("$.result.files[0].url").value("img_url"));
    }

    @Test
    @DisplayName("1건 조회: 책 간략 정보")
    void getBookSimple() throws Exception {
        BookSimpleResponse simpleResponse = getBookSimpleResponse(1L, "제목");

        when(bookService.getBookSimpleInformation(1L)).thenReturn(simpleResponse);

        ResultActions perform = mockMvc.perform(get("/shop/open/books/simple/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        verify(bookService, times(1)).getBookSimpleInformation(anyLong());

        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true))
                .andExpect(jsonPath("$.result.id").value(1))
                .andExpect(jsonPath("$.result.categories").doesNotExist())
                .andExpect(jsonPath("$.result.tags").doesNotExist())
                .andExpect(jsonPath("$.result.title").value("제목"))
                .andExpect(jsonPath("$.result.files[0].url").value("img_url"));
    }


    @Test
    @DisplayName("pageable 조회: 베스트 도서")
    void getBestAsPageable() throws Exception {
        PageRequest pageable = PageRequest.of(0, 20);
        PageImpl<BookSearchResponse> pageResponse = new PageImpl<>(result, pageable, 1234);

        when(bookService.getPageableEntireBookListOrderByCount(pageable)).thenReturn(pageResponse);

        ResultActions perform = mockMvc.perform(get("/shop/open/books/best")
                .param("page", "0")
                .param("size", "20")
                .contentType(MediaType.APPLICATION_JSON));

        verify(bookService, times(1)).getPageableEntireBookListOrderByCount(pageable);

        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true))
                .andExpect(jsonPath("$.result.pageable").exists())
                .andExpect(jsonPath("$.result.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.result.pageable.pageSize").value(20))
                .andExpect(jsonPath("$.result.numberOfElements").value(20))
                .andExpect(jsonPath("$.result.totalElements").value(1234));
    }
    
    @Test
    @DisplayName("pageable 조회: 카테고리 id")
    void getByCategoryIdAsPageable() throws Exception {
        PageRequest pageable = PageRequest.of(0, 20);
        PageImpl<BookSearchResponse> pageResponse = new PageImpl<>(result, pageable, 1234);

        when(bookService.getPageableBookListByCategoryId(1L, "test", pageable)).thenReturn(pageResponse);

        ResultActions perform =
                mockMvc.perform(get("/shop/open/books/categories/{id}", 1)
                        .param("page", "0")
                        .param("size", "20")
                        .param("sortCondition", "test")
                        .contentType(MediaType.APPLICATION_JSON));

        verify(bookService, times(1)).getPageableBookListByCategoryId(1L, "test", pageable);

        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true))
                .andExpect(jsonPath("$.result.pageable").exists())
                .andExpect(jsonPath("$.result.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.result.pageable.pageSize").value(20))
                .andExpect(jsonPath("$.result.numberOfElements").value(20))
                .andExpect(jsonPath("$.result.totalElements").value(1234));
    }
}
