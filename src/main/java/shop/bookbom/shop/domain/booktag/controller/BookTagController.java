package shop.bookbom.shop.domain.booktag.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.domain.booktag.dto.BookTagRequest;
import shop.bookbom.shop.domain.booktag.dto.BookTagResponse;
import shop.bookbom.shop.domain.booktag.service.BookTagService;

@RestController
@RequiredArgsConstructor
public class BookTagController {
    private final BookTagService bookTagService;

    // 책 태그 정보
    @GetMapping("/shop/bookTag/{id}")
    public ResponseEntity<Object> getBookTag(@PathVariable long id){
        List<BookTagResponse> bookTagResponses = bookTagService.getBookTagInformation(id);
        // 응답을 200으로 설정하고 본문으로 리스트를 넘겨줌
        return ResponseEntity.ok(bookTagResponses);
    }

    // 책 태그 등록
    @PostMapping("/shop/bookTag")
    public ResponseEntity<Integer> saveBookTag(@RequestBody BookTagRequest bookTagRequest){
        long bookId = bookTagRequest.getBookId();
        long tagId = bookTagRequest.getTagId();
        bookTagService.saveTagService(tagId,bookId);
        //리턴 값으로 http 상태코드 전송
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //책 태그 삭제
    @DeleteMapping("/shop/bookTag/{id}")
    public ResponseEntity<Integer> deleteBookTag(@PathVariable long id){
        bookTagService.deleteBookTagService(id);
        //리턴 값으로 http 상태코드 전송
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
