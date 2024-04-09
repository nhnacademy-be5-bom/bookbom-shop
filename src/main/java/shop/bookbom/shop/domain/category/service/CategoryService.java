package shop.bookbom.shop.domain.category.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.category.dto.CategoryDTO;
import shop.bookbom.shop.domain.category.dto.response.CategoryDepthResponse;
import shop.bookbom.shop.domain.category.entity.Category;
import shop.bookbom.shop.domain.category.entity.Status;
import shop.bookbom.shop.domain.category.exception.NoSuchCategoryNameException;
import shop.bookbom.shop.domain.category.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ObjectMapper mapper;

    @Transactional(readOnly = true)
    public Category findCategoryByCategoryName(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseThrow(NoSuchCategoryNameException::new);
    }

    @Transactional(readOnly = true)
    public CategoryDepthResponse getAllCategories() {
        List<Category> categories =
                categoryRepository.findAll();

        return CategoryDepthResponse.builder()
                .categories(
                        // jackson list-to-list 변환:
                        // List<category> -> List<CategoryDTO>
                        mapper.convertValue(categories,
                                mapper.getTypeFactory().constructCollectionType(List.class, CategoryDTO.class)))
                .build();
    }

    @Transactional(readOnly = true)
    public CategoryDepthResponse getAllDepthOneCategories() {
        // 상태 사용중, 부모 카테고리 없음(깊이1) 인 카테고리
        List<Category> categories = categoryRepository.findAllByStatusAndParentNull(Status.USED);

        // List<category> -> List<CategoryDTO>
        List<CategoryDTO> categoriesDto = mapper.convertValue(
                categories, mapper.getTypeFactory().constructCollectionType(List.class, CategoryDTO.class));

//        for (Category category : categories) {
//            categoriesDto.add(mapper.convertValue(category, CategoryDTO.class));
//        }

        return CategoryDepthResponse.builder()
                .categories(categoriesDto)
                .build();
    }

    @Transactional(readOnly = true)
    public CategoryDepthResponse getChildCategoriesByCategoryId(Long parentId) {
        // 상태 사용중, 부모 카테고리 id를 가지는 카테고리(깊이 2~3)
        List<Category> categories =
                categoryRepository.findAllByStatusAndParent_Id(Status.USED, parentId);

        return CategoryDepthResponse.builder()
                .categories(
                        // jackson list-to-list 변환:
                        // List<category> -> List<CategoryDTO>
                        mapper.convertValue(categories,
                                mapper.getTypeFactory().constructCollectionType(List.class, CategoryDTO.class)))
                .build();
    }

}
