package shop.bookbom.shop.domain.tag.dto.request;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.category.entity.Status;

//태그 등록을 요청받았을 때 넘어온 정보를 받아오는 dto
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TagCreateRequest {
    // 유효성 검증
    @NotNull
    @NotBlank
    private String name;
    public void setName(String name) {
        // 공백 제거
        this.name = name.replaceAll("\\s+", "");
    }
    @Enumerated(EnumType.STRING)
    private Status status;
}
