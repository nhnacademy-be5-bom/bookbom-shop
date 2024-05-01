package shop.bookbom.shop.domain.book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.book.dto.response.BookDetailResponse;
import shop.bookbom.shop.domain.book.dto.response.BookMediumResponse;
import shop.bookbom.shop.domain.book.dto.response.BookSimpleResponse;
import shop.bookbom.shop.domain.book.service.BookService;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class GetSingleBookRestController {

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


}
