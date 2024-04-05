package shop.bookbom.shop.domain.book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.book.dto.request.BookAddRequest;
import shop.bookbom.shop.domain.book.dto.request.BookUpdateRequest;
import shop.bookbom.shop.domain.book.dto.response.BookDetailResponse;
import shop.bookbom.shop.domain.book.service.BookService;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @GetMapping("/book/{id}")
    public CommonResponse<BookDetailResponse> getBook(@PathVariable("id") Long bookId) {
        bookService.getBook(bookId);

        return CommonResponse.successWithData(bookService.getBook(bookId));
    }

    @PutMapping("/book/update")
    public CommonResponse<Void> putBook(@RequestParam BookAddRequest bookAddRequest) {
        bookService.putBook(bookAddRequest);

        return CommonResponse.success();
    }

    @PutMapping("/book/update/{id}")
    public CommonResponse<Void> updateBook(@RequestParam BookUpdateRequest bookUpdateRequest,
                                           @PathVariable("id") Long bookId) {
        bookService.getBook(bookId);

        bookService.updateBook(bookUpdateRequest);

        return CommonResponse.success();
    }
}
