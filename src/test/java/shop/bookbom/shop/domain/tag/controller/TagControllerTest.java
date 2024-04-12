package shop.bookbom.shop.domain.tag.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.bookbom.shop.domain.category.entity.Status;
import shop.bookbom.shop.domain.tag.service.TagService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

}
