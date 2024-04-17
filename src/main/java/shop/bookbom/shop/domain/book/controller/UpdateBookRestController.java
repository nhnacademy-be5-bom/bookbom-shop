package shop.bookbom.shop.domain.book.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.book.dto.request.BookAddRequest;
import shop.bookbom.shop.domain.book.dto.request.BookUpdateRequest;
import shop.bookbom.shop.domain.book.service.BookService;

/**
 * packageName    : shop.bookbom.shop.domain.book.controller
 * fileName       : UpdateBookRestController
 * author         : UuLaptop
 * date           : 2024-04-17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-17        UuLaptop       최초 생성
 */
@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
@Slf4j
public class UpdateBookRestController {

    private final BookService bookService;

    @PutMapping("/book/update/new")
    public CommonResponse<Void> addBook(@RequestBody BookAddRequest bookAddRequest) {
        bookService.putBook(bookAddRequest);
        return CommonResponse.success();
    }

    @PutMapping("/book/update/{id}")
    public CommonResponse<Void> updateBook(@RequestParam BookUpdateRequest bookUpdateRequest,
                                           @PathVariable("id") Long bookId) {

        bookService.updateBook(bookUpdateRequest, bookId);

        return CommonResponse.success();
    }

    @PutMapping("/book/update/viewcount/{id},{count}")
    public CommonResponse<Void> updateBookView(@PathVariable("id") Long bookId,
                                               @PathVariable("count") Long viewCount) {

        bookService.updateBookViewCount(bookId, viewCount);

        return CommonResponse.success();
    }

    @PutMapping("/book/update/stock/{id},{stock}")
    public CommonResponse<Void> updateBookStock(@PathVariable("id") Long bookId,
                                                @PathVariable("count") Integer stock) {

        bookService.updateBookStock(bookId, stock);

        return CommonResponse.success();
    }

    @DeleteMapping("/book/delete/{id}")
    public CommonResponse<Void> deleteBook(@PathVariable("id") Long bookId) {

        bookService.deleteBook(bookId);

        return CommonResponse.success();
    }

}
