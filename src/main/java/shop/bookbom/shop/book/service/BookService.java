package shop.bookbom.shop.book.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.bookbom.shop.book.entity.Book;
import shop.bookbom.shop.book.repository.BookRepository;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    public Optional<Book> getBookInformation(Long bookId){
        return bookRepository.findById(bookId);
    }
}
