package shop.bookbom.shop.domain.category.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;

    @Builder
    @QueryProjection
    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
