package shop.bookbom.shop.domain.category.dto.response;

import static shop.bookbom.shop.domain.book.DtoToListHandler.processCategories;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.category.entity.Category;

/**
 * packageName    : shop.bookbom.shop.domain.category.dto.response
 * fileName       : CategoryNameAndChildResponse
 * author         : UuLaptop
 * date           : 2024-04-25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-25        UuLaptop       최초 생성
 */

@Getter
@NoArgsConstructor
public class CategoryNameAndChildResponse {
    private Long id;
    private String name;
    List<CategoryNameAndChildResponse> children;

    @Builder
    private CategoryNameAndChildResponse(Long id,
                                         String name,
                                         List<CategoryNameAndChildResponse> children) {
        this.id = id;
        this.name = name;
        this.children = children;
    }

    public static CategoryNameAndChildResponse from(Category category) {
        return CategoryNameAndChildResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .children(processCategories(category.getChild()))
                .build();
    }

}
