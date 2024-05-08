package shop.bookbom.shop.domain.book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.book.dto.BookSearchResponse;
import shop.bookbom.shop.domain.book.dto.response.BookDetailResponse;
import shop.bookbom.shop.domain.book.dto.response.BookMediumResponse;
import shop.bookbom.shop.domain.book.dto.response.BookSimpleResponse;
import shop.bookbom.shop.domain.book.service.BookSearchService;
import shop.bookbom.shop.domain.book.service.BookService;

@RestController
@RequestMapping("/shop/open")
@RequiredArgsConstructor
public class OpenBookController {
    private final BookSearchService bookSearchService;
    private final BookService bookService;

    @GetMapping("/search")
    public CommonResponse<Page<BookSearchResponse>> search(
            @RequestParam String keyword,
            @RequestParam String searchCond,
            @RequestParam String sortCond,
            Pageable pageable
    ) {
        return CommonResponse.successWithData(bookSearchService.search(pageable, keyword, searchCond, sortCond));
    }


    @GetMapping("/books/best")
    @CrossOrigin(origins = "*")
    public CommonResponse<Page<BookSearchResponse>> getBest(Pageable pageable) {
        return CommonResponse.successWithData(bookService.getPageableEntireBookListOrderByCount(pageable));
    }

    @GetMapping("/books/all")
    @CrossOrigin(origins = "*")
    public CommonResponse<Page<BookSearchResponse>> getAll(Pageable pageable) {
        return CommonResponse.successWithData(bookService.getPageableEntireBookList(pageable));
    }

    @GetMapping("/books/categories/{categoryId}")
    @CrossOrigin(origins = "*")
    public CommonResponse<Page<BookSearchResponse>> getByCategoryId(
            @PathVariable("categoryId") Long categoryId,
            Pageable pageable,
            @RequestParam String sortCondition
    ) {
        return CommonResponse.successWithData(
                bookService.getPageableBookListByCategoryId(categoryId, sortCondition, pageable));
    }

    @GetMapping("/books/detail/{id}")
    @CrossOrigin(origins = "*")
    public CommonResponse<BookDetailResponse> getBookDetail(@PathVariable("id") Long bookId) {
        return CommonResponse.successWithData(bookService.getBookDetailInformation(bookId));
    }

    @GetMapping("/books/medium/{id}")
    @CrossOrigin(origins = "*")
    public CommonResponse<BookMediumResponse> getBookMedium(@PathVariable("id") Long bookId) {
        return CommonResponse.successWithData(bookService.getBookMediumInformation(bookId));
    }

    @GetMapping("/books/simple/{id}")
    @CrossOrigin(origins = "*")
    public CommonResponse<BookSimpleResponse> getBookSimple(@PathVariable("id") Long bookId) {
        return CommonResponse.successWithData(bookService.getBookSimpleInformation(bookId));
    }
}
