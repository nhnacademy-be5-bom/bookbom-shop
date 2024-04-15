package shop.bookbom.shop.domain.booktag.controller;

import static shop.bookbom.shop.common.CommonResponse.success;
import static shop.bookbom.shop.common.CommonResponse.successWithData;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.booktag.dto.request.BookTagCreateRequest;
import shop.bookbom.shop.domain.booktag.dto.response.BookTagInfoResponse;
import shop.bookbom.shop.domain.booktag.service.BookTagService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class BookTagController {
    private final BookTagService bookTagService;

    // 책 태그 정보
    @GetMapping("/book/tag/{id}")
    public CommonResponse<List<BookTagInfoResponse>> getBookTag(@PathVariable long id) {
        List<BookTagInfoResponse> bookTagResponses = bookTagService.getBookTagInformation(id);
        // 응답을 200으로 설정하고 본문으로 리스트를 넘겨줌
        return successWithData(bookTagResponses);
    }

    // 책 태그 등록
    @PostMapping("/book/tag")
    public CommonResponse<Void> saveBookTag(final @Valid @RequestBody BookTagCreateRequest bookTagRequest) {
        long bookId = bookTagRequest.getBookId();
        long tagId = bookTagRequest.getTagId();
        bookTagService.saveTagService(tagId, bookId);
        return success();
    }

    //책 태그 삭제
    @DeleteMapping("/booktag/{id}")
    public CommonResponse<Void> deleteBookTag(@PathVariable long id) {
        bookTagService.deleteBookTagService(id);
        return success();
    }
}
