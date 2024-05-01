package shop.bookbom.shop.domain.book.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.book.dto.BookSearchResponse;
import shop.bookbom.shop.domain.book.service.BookSearchService;

@Slf4j
@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class BookController {
    private final BookSearchService bookSearchService;

    @GetMapping("/search")
    public CommonResponse<Page<BookSearchResponse>> search(
            @RequestParam String keyword,
            @RequestParam String searchCond,
            @RequestParam String sortCond,
            Pageable pageable
    ) {
        return CommonResponse.successWithData(bookSearchService.search(pageable, keyword, searchCond, sortCond));
    }
}
