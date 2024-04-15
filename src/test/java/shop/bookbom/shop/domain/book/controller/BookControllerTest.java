package shop.bookbom.shop.domain.book.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static shop.bookbom.shop.domain.book.utils.BookTestUtils.getBookSearchResponse;

import java.util.List;
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
import shop.bookbom.shop.domain.book.service.BookSearchService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookSearchService bookSearchService;

    @Test
    @DisplayName("도서 검색 요청")
    void search() throws Exception {
        //given
        String keyword = "title";
        PageRequest pageRequest = PageRequest.of(0, 5);
        when(bookSearchService.search(any(), any(), any())).thenReturn(
                new PageImpl<>(List.of(getBookSearchResponse()), pageRequest, 1));
        //when
        ResultActions perform = mockMvc.perform(get("/shop/search")
                .param("keyword", keyword));
        //then
        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true))
                .andExpect(jsonPath("$.result.content.size()").value(1));
    }
}
