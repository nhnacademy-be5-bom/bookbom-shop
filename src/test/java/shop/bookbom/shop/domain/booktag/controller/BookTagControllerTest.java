package shop.bookbom.shop.domain.booktag.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.bookbom.shop.argumentresolver.LoginArgumentResolver;
import shop.bookbom.shop.config.WebConfig;
import shop.bookbom.shop.domain.booktag.dto.response.BookTagInfoResponse;
import shop.bookbom.shop.domain.booktag.service.BookTagService;
import shop.bookbom.shop.domain.category.entity.Status;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = BookTagController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {WebConfig.class, LoginArgumentResolver.class}))
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
        BookTagInfoResponse response = BookTagInfoResponse.builder()
                .tagId(1L)
                .tagName("tagName")
                .status(Status.USED)
                .build();
        when(bookTagService.getBookTagInformation(bookId)).thenReturn(Collections.singletonList(response));

        // When & Then
        mockMvc.perform(get("/shop/books/tag/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.resultCode").value(200))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true))
                .andExpect(jsonPath("$.result[0].tagName").value(response.getTagName()))
                .andExpect(jsonPath("$.result[0].tagId").value(response.getTagId()))
                .andExpect(jsonPath("$.result[0].status").value(Status.USED.toString()));
    }

    @Test
    @DisplayName("책 태그 등록 - 성공")
    void saveBookTag_Success() throws Exception {
        // Given
        long tagId = 1L;
        long bookId = 2L;

        // When
        mockMvc.perform(post("/shop/books/tag")
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
        mockMvc.perform(delete("/shop/books/tag/{id}", bookTagId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.resultCode").value(200))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true));
    }

    @Test
    @DisplayName("책 태그 등록 - 유효성 검사 실패")
    void saveBookTag_ValidationFailed() throws Exception {
        // Given
        String requestBody = "{\"tagId\":0,\"bookId\":0}"; // 유효하지 않은 값
        // When & Then
        mockMvc.perform(post("/shop/books/tag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(jsonPath("$.header.successful").value(false))
                .andExpect(jsonPath("$.header.resultCode").value(400));
    }
}
