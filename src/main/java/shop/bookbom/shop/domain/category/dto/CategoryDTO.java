package shop.bookbom.shop.domain.category.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.bookcategory.entity.BookCategory;
import shop.bookbom.shop.domain.category.entity.Category;

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

    public static CategoryDTO from(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}
