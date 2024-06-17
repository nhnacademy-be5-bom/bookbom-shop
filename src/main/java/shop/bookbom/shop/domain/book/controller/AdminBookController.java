package shop.bookbom.shop.domain.book.controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.book.dto.BookSearchResponse;
import shop.bookbom.shop.domain.book.dto.request.BookAddRequest;
import shop.bookbom.shop.domain.book.dto.request.BookUpdateRequest;
import shop.bookbom.shop.domain.book.dto.response.BookUpdateResponse;
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
@RequestMapping("/shop/admin")
@RequiredArgsConstructor
public class AdminBookController {
    private final BookService bookService;

    @PutMapping("/books/update/new")
    public CommonResponse<Void> addBook(@RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
                                        @RequestPart("bookAddRequest") BookAddRequest bookAddRequest) {
        bookService.addBook(thumbnail, bookAddRequest);
        return CommonResponse.success();
    }

    @PutMapping("/books/update/{id}")
    public CommonResponse<Void> updateBook(@RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
                                           @RequestPart("bookUpdateRequest") BookUpdateRequest bookUpdateRequest,
                                           @PathVariable("id") Long bookId) {

        bookService.updateBook(thumbnail, bookUpdateRequest, bookId);
        return CommonResponse.success();
    }

    @DeleteMapping("/books/delete/{id}")
    public CommonResponse<Void> deleteBook(@PathVariable("id") Long bookId) {

        bookService.deleteBook(bookId);
        return CommonResponse.success();
    }

    @GetMapping("/books/update/{id}")
    @CrossOrigin(origins = "*")
    public CommonResponse<BookUpdateResponse> getBookUpdate(@PathVariable("id") Long bookId) {

        return CommonResponse.successWithData(bookService.getBookUpdateInformation(bookId));
    }

    @GetMapping("/books/all")
    public CommonResponse<Page<BookSearchResponse>> getAll(Pageable pageable,
                                                           @RequestParam String searchCondition) {

        String encodedCondition = URLDecoder.decode(searchCondition, StandardCharsets.UTF_8);

        return CommonResponse.successWithData(bookService.getBookListByTitle(encodedCondition, pageable));
    }
    
}
