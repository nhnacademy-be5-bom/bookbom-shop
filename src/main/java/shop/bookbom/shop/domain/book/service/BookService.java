package shop.bookbom.shop.domain.book.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.book.dto.request.BookAddRequest;
import shop.bookbom.shop.domain.book.dto.request.BookUpdateRequest;
import shop.bookbom.shop.domain.book.dto.response.BookDetailResponse;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.exception.BookNotFoundException;
import shop.bookbom.shop.domain.book.repository.BookRepository;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ObjectMapper mapper;

    @Transactional(readOnly = true)
    public BookDetailResponse getBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);

//        BookDetailResponse.builder()
//                .id(book.getId())
//                .title(book.getTitle())
//                .description(book.getDescription())
//                .index(book.getIndex())
//                .pubDate(book.getPubDate())
//                .isbn10(book.getIsbn10())
//                .isbn13(book.getIsbn13())
//                .cost(book.getCost())
//                .discountCost(book.getDiscountCost())
//                .packagable(book.getPackagable())
//                .stock(book.getStock())
//                .publisher(book.getPublisher())
//                .pointRate(book.getPointRate())
//                .authors(book.getAuthors())
//                .tags(book.getTags())
//                .categories(book.getCategories())
//                .build();

        return mapper.convertValue(book, BookDetailResponse.class);
    }

    @Transactional
    public void putBook(BookAddRequest bookAddRequest) {
        bookRepository.save(mapper.convertValue(bookAddRequest, Book.class));
    }

    @Transactional
    public void updateBook(BookUpdateRequest bookUpdateRequest) {
        bookRepository.save(mapper.convertValue(bookUpdateRequest, Book.class));
    }
}
