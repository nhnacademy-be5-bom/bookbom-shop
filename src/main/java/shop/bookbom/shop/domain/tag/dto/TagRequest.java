package shop.bookbom.shop.domain.tag.dto;

import lombok.Getter;
import shop.bookbom.shop.domain.category.entity.Status;

//태그 등록을 요청받았을 때 넘어온 정보를 받아오는 dto
@Getter
public class TagRequest {
    String name;
    Status status;

    public TagRequest(String name, Status status) {
        //이름과 상태의 유효성 검증
        if (name == null) {
            throw new IllegalArgumentException("Invalid name");
        }
        if (status != Status.USED && status != Status.DEL) {
            throw new IllegalArgumentException("Invalid status");
        }
        this.name = name;
        this.status = status;
    }
}
