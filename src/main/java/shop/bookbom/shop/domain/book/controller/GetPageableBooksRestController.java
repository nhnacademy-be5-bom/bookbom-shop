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
import shop.bookbom.shop.domain.book.service.BookService;

/**
 * packageName    : shop.bookbom.shop.domain.book.controller
 * fileName       : BookPageableRestController
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
public class GetPageableBooksRestController {

    private final BookService bookService;

    @GetMapping("/books/best")
    @CrossOrigin(origins = "*")
    public CommonResponse<Page<BookSearchResponse>> getBest(Pageable pageable) {

        return CommonResponse.successWithData(bookService.getPageableEntireBookListOrderByCount(pageable));
    }

    @GetMapping("/books/all")
    @CrossOrigin(origins = "*")
    public CommonResponse<Page<BookSearchResponse>> getAll(Pageable pageable) {
        // #TODO 관리자 페이지로 이동
        return CommonResponse.successWithData(bookService.getPageableEntireBookList(pageable));
    }

    @GetMapping("/books/category/{categoryId}")
    @CrossOrigin(origins = "*")
    public CommonResponse<Page<BookSearchResponse>> getByCategoryId(
            @PathVariable("categoryId") Long categoryId,
            Pageable pageable,
            @RequestParam String sortCondition) {

        return CommonResponse.successWithData(
                bookService.getPageableBookListByCategoryId(categoryId, sortCondition, pageable));
    }
}
