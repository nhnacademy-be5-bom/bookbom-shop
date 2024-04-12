package shop.bookbom.shop.domain.booktag.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.bookbom.shop.domain.booktag.dto.BookTagResponse;
import shop.bookbom.shop.domain.booktag.service.BookTagService;

import java.util.Collections;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookTagController.class)
class BookTagControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private BookTagService bookTagService;

    @Test
    @DisplayName("책 태그 정보 조회 - 성공")
    void getBookTag_Success() throws Exception {
        // Given
        long bookId = 1L;
        BookTagResponse response = new BookTagResponse();
        response.setTagName("tagName");
        when(bookTagService.getBookTagInformation(bookId)).thenReturn(Collections.singletonList(response));

        // When & Then
        mockMvc.perform(get("/shop/booktag/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.resultCode").value(200))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true))
                .andExpect(jsonPath("$.result[0].tagName").value(response.getTagName()));
    }

    @Test
    @DisplayName("책 태그 등록 - 성공")
    void saveBookTag_Success() throws Exception {
        // Given
        long tagId = 1L;
        long bookId = 2L;

        // When
        mockMvc.perform(post("/shop/booktag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tagId\":1,\"bookId\":2}"))
                .andExpect(status().isOk());
        // then
        verify(bookTagService, times(1)).saveTagService(tagId, bookId);
    }

    @Test
    @DisplayName("책 태그 삭제 - 성공")
    void deleteBookTag_Success() throws Exception {
        // Given
        long bookTagId = 1L;
        doNothing().when(bookTagService).deleteBookTagService(bookTagId);

        // When & Then
        mockMvc.perform(delete("/shop/booktag/{id}", bookTagId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.resultCode").value(200))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true));
    }
}
