package shop.bookbom.shop.domain.booktag.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.repository.BookRepository;
import shop.bookbom.shop.domain.booktag.dto.BookTagResponse;
import shop.bookbom.shop.domain.booktag.entity.BookTag;
import shop.bookbom.shop.domain.booktag.exception.BookTagAlreadyExistException;
import shop.bookbom.shop.domain.booktag.exception.BookTagNotFoundException;
import shop.bookbom.shop.domain.booktag.repository.BookTagRepository;
import shop.bookbom.shop.domain.category.entity.Status;
import shop.bookbom.shop.domain.tag.entity.Tag;
import shop.bookbom.shop.domain.tag.repository.TagRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookTagServiceTest {

    @Mock
    private BookTagRepository bookTagRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookTagService bookTagService;

    @Test
    @DisplayName("책 태그 정보 조회 - 성공")
    void getBookTagInformation_Success() {
        // Given
        long bookId = 1L;
        Tag tag = Tag.builder()
                .name("tagName")
                .status(Status.USED)
                .build();
        Book book = Book.builder().build();
        BookTag bookTag = BookTag.builder()
                .tag(tag)
                .book(book)
                .build();
        when(bookTagRepository.findAllByBookId(bookId)).thenReturn(Collections.singletonList(bookTag));

        // When
        List<BookTagResponse> result = bookTagService.getBookTagInformation(bookId);

        // Then
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("tagName", result.get(0).getTagName());
    }

    @Test
    @DisplayName("책 태그 정보 조회 - 실패: 해당하는 책 태그가 없는 경우")
    void getBookTagInformation_NotFound() {
        // Given
        long bookId = 1L;
        when(bookTagRepository.findAllByBookId(bookId)).thenReturn(Collections.emptyList());

        // When & Then
        assertThrows(BookTagNotFoundException.class, () -> bookTagService.getBookTagInformation(bookId));
    }

    @Test
    @DisplayName("책 태그 등록 - 성공")
    void saveTagService_Success() {
        // Given
        long tagId = 1L;
        long bookId = 1L;
        Tag tag = Tag.builder().build();
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));
        Book book = Book.builder().build();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookTagRepository.findByBookIdAndTagId(bookId, tagId)).thenReturn(Optional.empty());

        // When
        assertDoesNotThrow(() -> bookTagService.saveTagService(tagId, bookId));

        // Then
        verify(bookTagRepository, times(1)).save(any(BookTag.class));
    }

    @Test
    @DisplayName("책 태그 등록 - 실패: 이미 해당 책 태그가 존재하는 경우")
    void saveTagService_AlreadyExists() {
        // Given
        long tagId = 1L;
        long bookId = 1L;
        Tag tag = Tag.builder().build();
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));
        Book book = Book.builder().build();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        BookTag bookTag = BookTag.builder().build();
        when(bookTagRepository.findByBookIdAndTagId(bookId, tagId)).thenReturn(Optional.of(bookTag));

        // When & Then
        assertThrows(BookTagAlreadyExistException.class, () -> bookTagService.saveTagService(tagId, bookId));
        verify(bookTagRepository, never()).save(any(BookTag.class));
    }

    @Test
    @DisplayName("책 태그 삭제 - 성공")
    void deleteBookTagService_Success() {
        // Given
        long bookTagId = 1L;
        BookTag bookTag = BookTag.builder().build();
        when(bookTagRepository.findById(bookTagId)).thenReturn(Optional.of(bookTag));

        // When
        assertDoesNotThrow(() -> bookTagService.deleteBookTagService(bookTagId));

        // Then
        verify(bookTagRepository, times(1)).deleteById(bookTagId);
    }

    @Test
    @DisplayName("책 태그 삭제 - 실패: 해당하는 책 태그가 없는 경우")
    void deleteBookTagService_NotFound() {
        // Given
        long bookTagId = 1L;
        when(bookTagRepository.findById(bookTagId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(BookTagNotFoundException.class, () -> bookTagService.deleteBookTagService(bookTagId));
        verify(bookTagRepository, never()).deleteById(anyLong());
    }
}
