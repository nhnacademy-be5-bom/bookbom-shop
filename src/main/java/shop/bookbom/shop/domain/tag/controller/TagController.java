package shop.bookbom.shop.domain.tag.controller;

import static shop.bookbom.shop.common.CommonResponse.success;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.category.entity.Status;
import shop.bookbom.shop.domain.tag.dto.request.TagCreateRequest;
import shop.bookbom.shop.domain.tag.service.TagService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/shop")
public class TagController {
    private final TagService tagService;

    //태그 등록
    @PostMapping("/tag")
    public CommonResponse<Void> saveTag(final @Valid @RequestBody TagCreateRequest tagRequest) {
        String name = tagRequest.getName();
        Status status = tagRequest.getStatus();
        //태그 등록함수 실행
        tagService.saveTagService(name, status);
        return success();
    }

    //태그 삭제
    @DeleteMapping("/tag/{tagId}")
    public CommonResponse<Void> deleteTag(@PathVariable long tagId) {
        //태그 삭제 함수 실행
        tagService.deleteTagService(tagId);
        return success();
    }
}
