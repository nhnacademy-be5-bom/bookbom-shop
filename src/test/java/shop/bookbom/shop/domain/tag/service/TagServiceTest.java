package shop.bookbom.shop.domain.tag.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.bookbom.shop.domain.category.entity.Status;
import shop.bookbom.shop.domain.tag.entity.Tag;
import shop.bookbom.shop.domain.tag.exception.TagAlreadyExistException;
import shop.bookbom.shop.domain.tag.exception.TagNotFoundException;
import shop.bookbom.shop.domain.tag.repository.TagRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
    @Mock
    TagRepository tagRepository;

    @InjectMocks
    TagService tagService;

    @Test
    @DisplayName("태그 등록 - 성공")
    void saveTagService_Success() {
        // Given
        String tagName = "TestTag";
        Status tagStatus = Status.USED;
        when(tagRepository.findByName(tagName)).thenReturn(Optional.empty());

        // When
        tagService.saveTagService(tagName, tagStatus);

        // Then
        verify(tagRepository, times(1)).findByName(tagName);
        verify(tagRepository, times(1)).save(ArgumentMatchers.any(Tag.class));
    }

    @Test
    @DisplayName("태그 등록 - 이미 존재하는 경우")
    void saveTagService_AlreadyExists() {
        // Given
        String tagName = "ExistingTag";
        Status tagStatus = Status.USED;
        Tag existingTag = Tag.builder().name(tagName).status(tagStatus).build();
        when(tagRepository.findByName(tagName)).thenReturn(Optional.of(existingTag));

        // When / Then
        assertThrows(TagAlreadyExistException.class, () -> tagService.saveTagService(tagName, tagStatus));
    }

    @Test
    @DisplayName("태그 삭제 - 성공")
    void deleteTagService_Success() {
        // Given
        long tagId = 1L;
        Tag existingTag = Tag.builder().build();
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(existingTag));

        // When
        tagService.deleteTagService(tagId);

        // Then
        verify(tagRepository, times(1)).findById(tagId);
        verify(tagRepository, times(1)).deleteById(tagId);
    }

    @Test
    @DisplayName("태그 삭제 - 태그를 찾을 수 없는 경우")
    void deleteTagService_TagNotFound() {
        // Given
        long tagId = 1L;
        when(tagRepository.findById(tagId)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(TagNotFoundException.class, () -> tagService.deleteTagService(tagId));
    }
}
