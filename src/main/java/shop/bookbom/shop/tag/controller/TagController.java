package shop.bookbom.shop.tag.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.bookbom.shop.category.entity.Status;
import shop.bookbom.shop.tag.dto.TagRequest;
import shop.bookbom.shop.tag.service.TagService;

@RequiredArgsConstructor
@RestController
public class TagController {
    private final TagService tagService;

    //태그 등록
    @PostMapping("/tag")
    public ResponseEntity<Integer> saveTag(@RequestBody TagRequest tagRequest) {
        String name = tagRequest.getName();
        Status status = tagRequest.getStatus();
        //태그 등록함수 실행
        tagService.saveTagService(name, status);
        //리턴 값으로 http 상태코드 전송
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //태그 삭제
    @DeleteMapping("/tag/{tagId}")
    public ResponseEntity<Integer> deleteTag(@PathVariable long tagId) {
        //태그 삭제 함수 실행
        tagService.deleteTagService(tagId);
        //리턴 값으로 http 상태코드 전송
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
