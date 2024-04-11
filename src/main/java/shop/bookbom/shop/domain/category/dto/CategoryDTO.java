package shop.bookbom.shop.domain.category.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;

    @Builder
    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
