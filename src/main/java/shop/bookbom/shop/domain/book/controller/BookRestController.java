package shop.bookbom.shop.domain.book.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.book.dto.request.BookAddRequest;
import shop.bookbom.shop.domain.book.dto.request.BookUpdateRequest;
import shop.bookbom.shop.domain.book.exception.BookNotFoundException;
import shop.bookbom.shop.domain.book.service.BookService;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
@Slf4j
public class BookRestController {

    private final BookService bookService;

    @GetMapping("/book/detail/{id}")
    @CrossOrigin(origins = "*")
    public CommonResponse<?> getBookDetail(@PathVariable("id") Long bookId) {
        try {
            bookService.exists(bookId);
            return CommonResponse.successWithData(bookService.getBookDetailInformation(bookId));

        } catch (BookNotFoundException e) {
            return CommonResponse.fail(e.getErrorCode());
        }
    }

    @GetMapping("/book/simple/{id}")
    @CrossOrigin(origins = "*")
    public CommonResponse<?> getBookSimple(@PathVariable("id") Long bookId) {
        try {
            bookService.exists(bookId);
            return CommonResponse.successWithData(bookService.getBookSimpleInformation(bookId));

        } catch (BookNotFoundException e) {
            return CommonResponse.fail(e.getErrorCode());
        }
    }

    @GetMapping("/book/medium/{id}")
    @CrossOrigin(origins = "*")
    public CommonResponse<?> getBookMedium(@PathVariable("id") Long bookId) {
        try {
            bookService.exists(bookId);
            return CommonResponse.successWithData(bookService.getBookMediumInformation(bookId));

        } catch (BookNotFoundException e) {
            return CommonResponse.fail(e.getErrorCode());
        }
    }

    @PutMapping("/book/update")
    public CommonResponse<Void> putBook(@RequestBody BookAddRequest bookAddRequest) {
        bookService.putBook(bookAddRequest);

        return CommonResponse.success();
    }

    @PutMapping("/book/update/{id}")
    public CommonResponse<Void> updateBook(@RequestParam BookUpdateRequest bookUpdateRequest,
                                           @PathVariable("id") Long bookId) {
        bookService.exists(bookId);

        bookService.updateBook(bookUpdateRequest);

        return CommonResponse.success();
    }
}
