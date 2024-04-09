package shop.bookbom.shop.domain.category.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private List<CategoryDTO> child;

    @Builder
    public CategoryDTO(Long id, String name, List<CategoryDTO> child) {
        this.id = id;
        this.name = name;
        this.child = child;
    }
}
