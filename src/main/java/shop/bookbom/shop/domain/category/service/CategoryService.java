package shop.bookbom.shop.domain.category.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.book.exception.IdMismatchException;
import shop.bookbom.shop.domain.bookcategory.repository.BookCategoryRepository;
import shop.bookbom.shop.domain.category.dto.CategoryDTO;
import shop.bookbom.shop.domain.category.dto.request.CategoryAddRequest;
import shop.bookbom.shop.domain.category.dto.request.CategoryUpdateRequest;
import shop.bookbom.shop.domain.category.dto.response.CategoryDepthResponse;
import shop.bookbom.shop.domain.category.dto.response.CategoryNameAndChildResponse;
import shop.bookbom.shop.domain.category.entity.Category;
import shop.bookbom.shop.domain.category.entity.Status;
import shop.bookbom.shop.domain.category.exception.CategoryHasBookLeftException;
import shop.bookbom.shop.domain.category.exception.CategoryHasChildLeftException;
import shop.bookbom.shop.domain.category.exception.NoSuchCategoryNameException;
import shop.bookbom.shop.domain.category.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final BookCategoryRepository bookCategoryRepository;
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
    public List<CategoryDTO> getAllDepthOneCategories() {
        // 상태 사용중, 부모 카테고리 없음(깊이1) 인 카테고리
        return categoryRepository.findAllAtDepthOne();
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> getChildCategoriesByCategoryId(Long parentId) {
        // 상태 사용중, 부모 카테고리 id를 가지는 카테고리(깊이 2~3)
        return categoryRepository.findAllByParentId(parentId);
    }

    @Transactional(readOnly = true)
    public CategoryNameAndChildResponse getCategoryNameAndChildCategoriesByCategoryId(Long parentId) {

        return categoryRepository.getNameAndChildrenById(parentId);
    }

    @Transactional
    @Modifying
    public void addCategory(CategoryAddRequest categoryAddRequest) {
        Category newCategoryEntity = Category.builder()
                .name(categoryAddRequest.getName())
                .status(Status.USED)
                .parent(categoryRepository.getReferenceById(categoryAddRequest.getParentId()))
                .build();

        for (Long childCategoryId : categoryAddRequest.getChildId()) {
            newCategoryEntity.addChildCategory(categoryRepository.getReferenceById(childCategoryId));
        }

        categoryRepository.save(newCategoryEntity);
    }

    @Transactional
    @Modifying
    public void updateCategory(CategoryUpdateRequest categoryUpdateRequest, Long categoryId) {

        Category targetCategory = categoryRepository.getReferenceById(categoryId);

        if (categoryUpdateRequest.getId().equals(categoryId)) {
            if (targetCategory.getChild().isEmpty()) {

                targetCategory.update(categoryUpdateRequest);

            } else {
                throw new CategoryHasChildLeftException();
            }
        } else {
            throw new IdMismatchException();
        }

        categoryRepository.save(targetCategory);
    }

    @Transactional
    @Modifying
    public void deleteCategory(Long categoryId) {

        Category targetCategory = categoryRepository.getReferenceById(categoryId);

        if (!targetCategory.getChild().isEmpty()) {

            categoryRepository.hasBookLeftInsideOf(categoryId).orElseThrow(CategoryHasBookLeftException::new);
            targetCategory.delete();

        } else {
            throw new CategoryHasChildLeftException();
        }

        categoryRepository.save(targetCategory);
    }

}
