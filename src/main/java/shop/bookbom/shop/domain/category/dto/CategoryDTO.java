package shop.bookbom.shop.domain.category.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.bookcategory.entity.BookCategory;

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

    public static CategoryDTO from(BookCategory bookCategory) {
        return CategoryDTO.builder()
                .id(bookCategory.getCategory().getId())
                .name(bookCategory.getCategory().getName())
                .build();
    }

}
