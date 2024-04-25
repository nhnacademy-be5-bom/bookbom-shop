package shop.bookbom.shop.domain.book.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static shop.bookbom.shop.domain.book.utils.BookTestUtils.getBookAddRequest;
import static shop.bookbom.shop.domain.book.utils.BookTestUtils.getBookDetailResponse;
import static shop.bookbom.shop.domain.book.utils.BookTestUtils.getBookFileTypeEntity;
import static shop.bookbom.shop.domain.book.utils.BookTestUtils.getCategoryEntity;
import static shop.bookbom.shop.domain.book.utils.BookTestUtils.getPointRateEntity;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.bookbom.shop.common.file.ObjectService;
import shop.bookbom.shop.domain.author.repository.AuthorRepository;
import shop.bookbom.shop.domain.book.dto.request.BookAddRequest;
import shop.bookbom.shop.domain.book.dto.response.BookDetailResponse;
import shop.bookbom.shop.domain.book.exception.BookNotFoundException;
import shop.bookbom.shop.domain.book.repository.BookRepository;
import shop.bookbom.shop.domain.bookauthor.repository.BookAuthorRepository;
import shop.bookbom.shop.domain.bookcategory.repository.BookCategoryRepository;
import shop.bookbom.shop.domain.bookfile.repository.BookFileRepository;
import shop.bookbom.shop.domain.bookfiletype.repository.BookFileTypeRepository;
import shop.bookbom.shop.domain.booktag.repository.BookTagRepository;
import shop.bookbom.shop.domain.category.repository.CategoryRepository;
import shop.bookbom.shop.domain.file.repository.FileRepository;
import shop.bookbom.shop.domain.pointrate.repository.PointRateRepository;
import shop.bookbom.shop.domain.publisher.repository.PublisherRepository;
import shop.bookbom.shop.domain.tag.repository.TagRepository;

/**
 * packageName    : shop.bookbom.shop.domain.book.service
 * fileName       : BookServiceTest
 * author         : UuLaptop
 * date           : 2024-04-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-22        UuLaptop       최초 생성
 */
@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    BookRepository bookRepository;
    @Mock
    PublisherRepository publisherRepository;
    @Mock
    PointRateRepository pointRateRepository;
    @Mock
    AuthorRepository authorRepository;
    @Mock
    BookAuthorRepository bookAuthorRepository;
    @Mock
    TagRepository tagRepository;
    @Mock
    BookTagRepository bookTagRepository;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    BookCategoryRepository bookCategoryRepository;
    @Mock
    FileRepository fileRepository;
    @Mock
    BookFileTypeRepository bookFileTypeRepository;
    @Mock
    BookFileRepository bookFileRepository;
    @Mock
    ObjectService objectService;
    @InjectMocks
    BookService bookService;

    @Test
    @DisplayName("책 상세정보 조회")
    void getBookDetailInformation() {
        when(bookRepository.getBookDetailInfoById(anyLong())).thenReturn(
                Optional.of(getBookDetailResponse(1L, "테스트 책")));

        BookDetailResponse response = bookService.getBookDetailInformation(1L);

        verify(bookRepository, times(1)).getBookDetailInfoById(1L);
        assertEquals("테스트 책", response.getTitle());
    }

    @Test
    @DisplayName("책 상세정보 조회: 존재하지 않는 책 예외")
    void getBookDetailInformation_BookNotFoundException() {
        when(bookRepository.getBookDetailInfoById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.getBookDetailInformation(1L));

        verify(bookRepository, times(1)).getBookDetailInfoById(1L);
    }

    @Test
    @DisplayName("책 중간정보 조회")
    void getBookMediumInformation() {
    }

    @Test
    @DisplayName("책 중간정보 조회: 존재하지 않는 책 예외")
    void getBookMediumInformation_BookNotFoundException() {
    }

    @Test
    void getBookSimpleInformation() {
    }

    @Test
    void getBookSimpleInformation_BookNotFoundException() {
    }

    @Test
    void getPageableEntireBookList() {
    }

    @Test
    void getPageableEntireBookListOrderByCount() {
    }

    @Test
    void getPageableBookListByCategoryId() {
    }

    @Test
    @DisplayName("책 저장: 모든 필드가 존재")
    void addBook() {
        BookAddRequest request = getBookAddRequest("테스트");
        when(pointRateRepository.getReferenceById(1L)).thenReturn(getPointRateEntity());
        when(tagRepository.existsByName(anyString())).thenReturn(false);
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.ofNullable(getCategoryEntity()));
        when(objectService.getUrl(anyString(), anyString())).thenReturn("URL");
        when(bookFileTypeRepository.getReferenceById(1L)).thenReturn(getBookFileTypeEntity());

        bookService.addBook(request);

        verify(publisherRepository, times(1)).save(any());
        verify(pointRateRepository, times(1)).getReferenceById(1L);

        verify(tagRepository, times(2)).existsByName(anyString());
        verify(tagRepository, times(2)).save(any());
        verify(bookTagRepository, times(2)).save(any());

        verify(authorRepository, times(2)).save(any());
        verify(bookAuthorRepository, times(2)).save(any());

        verify(categoryRepository, times(2)).findByName(anyString());
        verify(bookCategoryRepository, times(2)).save(any());

        verify(fileRepository, times(1)).save(any());
        verify(bookFileTypeRepository, times(1)).getReferenceById(1L);
        verify(bookFileRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("책 수정")
    void updateBook() {
    }

    @Test
    void updateBookViewCount() {
    }

    @Test
    void updateBookStock() {
    }

    @Test
    void deleteBook() {
    }

    @Test
    void reviveBook() {
    }
}
