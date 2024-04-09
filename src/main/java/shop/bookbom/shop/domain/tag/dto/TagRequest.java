package shop.bookbom.shop.domain.tag.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import shop.bookbom.shop.domain.category.entity.Status;

//태그 등록을 요청받았을 때 넘어온 정보를 받아오는 dto
@Getter
@Data
public class TagRequest {
    // 유효성 검증
    @NotNull
    private String name;
    @Enumerated(EnumType.STRING)
    private Status status;
}
