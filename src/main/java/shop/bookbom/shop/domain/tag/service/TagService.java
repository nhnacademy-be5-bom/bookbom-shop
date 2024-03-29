package shop.bookbom.shop.domain.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.bookbom.shop.domain.category.entity.Status;
import shop.bookbom.shop.domain.tag.entity.Tag;
import shop.bookbom.shop.domain.tag.repository.TagRepository;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    //태그 등록
    public void saveTagService(String name, Status status) {
        //받아온 새로운 Tag 엔티티 생성
        Tag tag = new Tag(name, status);
        //레포지토리에 접근해 저장 실행
        tagRepository.save(tag);
    }

    //태그 삭제
    public void deleteTagService(long tagId) {
        //레포지토리에 접근해 삭제 실행
        tagRepository.deleteById(tagId);
    }
}
