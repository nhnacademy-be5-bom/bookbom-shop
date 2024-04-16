package shop.bookbom.shop.domain.book.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import shop.bookbom.shop.domain.book.dto.response.BookDetailResponse;
import shop.bookbom.shop.domain.book.dto.response.BookMediumResponse;
import shop.bookbom.shop.domain.book.dto.response.BookSimpleResponse;
import shop.bookbom.shop.domain.book.service.BookService;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
@Slf4j
public class BookRestController {

    private final BookService bookService;

    @GetMapping("/book/detail/{id}")
    @CrossOrigin(origins = "*")
    public CommonResponse<BookDetailResponse> getBookDetail(@PathVariable("id") Long bookId) {

        return CommonResponse.successWithData(bookService.getBookDetailInformation(bookId));
    }

    @GetMapping("/book/medium/{id}")
    @CrossOrigin(origins = "*")
    public CommonResponse<BookMediumResponse> getBookMedium(@PathVariable("id") Long bookId) {

        return CommonResponse.successWithData(bookService.getBookMediumInformation(bookId));
    }

    @GetMapping("/book/simple/{id}")
    @CrossOrigin(origins = "*")
    public CommonResponse<BookSimpleResponse> getBookSimple(@PathVariable("id") Long bookId) {

        return CommonResponse.successWithData(bookService.getBookSimpleInformation(bookId));
    }

    @GetMapping("/books/best")
    @CrossOrigin(origins = "*")
    public CommonResponse<Page<BookMediumResponse>> getBestAsPageable(Pageable pageable) {

        return CommonResponse.successWithData(bookService.getPageableEntireBookListOrderByCount(pageable));
    }

    @GetMapping("/books/all")
    @CrossOrigin(origins = "*")
    public CommonResponse<Page<BookMediumResponse>> getAllAsPageable(Pageable pageable) {
        // #TODO 관리자 페이지로 이동
        return CommonResponse.successWithData(bookService.getPageableEntireBookList(pageable));
    }

    @GetMapping("/books/category/{categoryId}")
    @CrossOrigin(origins = "*")
    public CommonResponse<Page<BookMediumResponse>> getByCategoryIdAsPageable(
            @PathVariable("categoryId") Long categoryId,
            Pageable pageable) {

        return CommonResponse.successWithData(bookService.getPageableBookListByCategoryId(categoryId, pageable));
    }

    @PutMapping("/book/update/new")
    public CommonResponse<Void> putBook(@RequestBody BookAddRequest bookAddRequest) {
        bookService.putBook(bookAddRequest);
        return CommonResponse.success();
    }

    @PutMapping("/book/update/{id}")
    public CommonResponse<Void> updateBook(@RequestParam BookUpdateRequest bookUpdateRequest,
                                           @PathVariable("id") Long bookId) {

        bookService.updateBook(bookUpdateRequest);

        return CommonResponse.success();
    }
}
