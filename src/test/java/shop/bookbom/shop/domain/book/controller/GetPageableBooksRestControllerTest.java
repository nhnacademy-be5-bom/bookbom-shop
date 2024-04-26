package shop.bookbom.shop.domain.book.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static shop.bookbom.shop.domain.book.utils.BookTestUtils.getBookSearchResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shop.bookbom.shop.domain.book.dto.BookSearchResponse;
import shop.bookbom.shop.domain.book.service.BookService;

/**
 * packageName    : shop.bookbom.shop.domain.book.controller
 * fileName       : GetPageableBooksRestControllerTest
 * author         : UuLaptop
 * date           : 2024-04-17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-17        UuLaptop       최초 생성
 */
@ExtendWith(MockitoExtension.class)
@WebMvcTest(GetPageableBooksRestController.class)
class GetPageableBooksRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService bookService;
    ObjectMapper mapper;

    List<BookSearchResponse> result = new ArrayList<>();

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 20; i++) {
            result.add(getBookSearchResponse());
        }
    }

    @Test
    @DisplayName("pageable 조회: 베스트 도서")
    void getBestAsPageable() throws Exception {
        PageRequest pageable = PageRequest.of(0, 20);
        PageImpl<BookSearchResponse> pageResponse = new PageImpl<>(result, pageable, 1234);

        when(bookService.getPageableEntireBookListOrderByCount(pageable)).thenReturn(pageResponse);

        ResultActions perform = mockMvc.perform(get("/shop/books/best")
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
    @DisplayName("pageable 조회: 전체")
    void getAllAsPageable() throws Exception {
        PageRequest pageable = PageRequest.of(0, 20);
        PageImpl<BookSearchResponse> pageResponse = new PageImpl<>(result, pageable, 3433);

        when(bookService.getPageableEntireBookList(pageable)).thenReturn(pageResponse);

        ResultActions perform = mockMvc.perform(get("/shop/books/all")
                .param("page", "0")
                .param("size", "20")
                .contentType(MediaType.APPLICATION_JSON));

        verify(bookService, times(1)).getPageableEntireBookList(pageable);

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
                .andExpect(jsonPath("$.result.totalElements").value(3433));
    }

    @Test
    @DisplayName("pageable 조회: 카테고리 id")
    void getByCategoryIdAsPageable() throws Exception {
        PageRequest pageable = PageRequest.of(0, 20);
        PageImpl<BookSearchResponse> pageResponse = new PageImpl<>(result, pageable, 1234);

        when(bookService.getPageableBookListByCategoryId(1L, "", pageable)).thenReturn(pageResponse);

        ResultActions perform =
                mockMvc.perform(get("/shop/books/category/{id}", 1)
                        .param("page", "0")
                        .param("size", "20")
                        .contentType(MediaType.APPLICATION_JSON));

        verify(bookService, times(1)).getPageableBookListByCategoryId(1L, "", pageable);

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
