package shop.bookbom.shop.domain.book.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static shop.bookbom.shop.domain.book.utils.BookTestUtils.getBookDetailResponse;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.bookbom.shop.common.file.ObjectService;
import shop.bookbom.shop.domain.author.repository.AuthorRepository;
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
    @InjectMocks
    BookService bookService;
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
    void getBookMediumInformation() {
    }

    @Test
    void getBookSimpleInformation() {
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
    void addBook() {
    }

    @Test
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
