package shop.bookbom.shop.domain.tag.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.bookbom.shop.domain.category.entity.Status;
import shop.bookbom.shop.domain.tag.service.TagService;

@WebMvcTest(TagController.class)
class TagControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TagService tagService;

    @Test
    @DisplayName("태그 등록")
    void addTag() throws Exception {
        // Given/ When
        mockMvc.perform(post("/shop/tag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"TestTag\", \"status\": \"USED\"}"))
                .andExpect(status().isOk());

        // Then
        verify(tagService, times(1)).saveTagService("TestTag", Status.USED);
    }

    @Test
    @DisplayName("태그 삭제")
    void deleteTag() throws Exception {
        // Given
        long tagId = 1L;

        // When
        mockMvc.perform(delete("/shop/tag/{tagId}", tagId))
                .andExpect(status().isOk());

        // Then
        verify(tagService, times(1)).deleteTagService(tagId);
    }

    @Test
    @DisplayName("태그 등록 - 유효성 검사 실패")
    void addTag_ValidationFailed() throws Exception {
        // Given
        String requestBody = "{\"name\": \"\", \"status\": \"USED\"}"; // 빈 이름
        // When & Then
        mockMvc.perform(post("/shop/tag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(jsonPath("$.header.successful").value(false))
                .andExpect(jsonPath("$.header.resultCode").value(400));
    }

}
