package shop.bookbom.shop.domain.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonListResponse;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.category.dto.CategoryDTO;
import shop.bookbom.shop.domain.category.dto.request.CategoryAddRequest;
import shop.bookbom.shop.domain.category.dto.request.CategoryUpdateRequest;
import shop.bookbom.shop.domain.category.dto.response.CategoryNameAndChildResponse;
import shop.bookbom.shop.domain.category.service.CategoryService;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
@Slf4j
public class CategoryRestController {
    private final CategoryService categoryService;

    @GetMapping("/open/categories/depth1")
    @CrossOrigin(origins = "*")
    public CommonListResponse<CategoryDTO> getDepthOneCategories() {
        return CommonListResponse.successWithList(categoryService.getAllDepthOneCategories());
    }

    @GetMapping("/open/categories/{parentId}")
    @CrossOrigin(origins = "*")
    public CommonListResponse<CategoryDTO> getChildCategoriesOf(@PathVariable("parentId") Long parentId) {
        return CommonListResponse.successWithList(categoryService.getChildCategoriesByCategoryId(parentId));
    }

    @GetMapping("/open/categories/nameandchild/{parentId}")
    @CrossOrigin(origins = "*")
    public CommonResponse<CategoryNameAndChildResponse> getNameAndChildCategoriesOf(
            @PathVariable("parentId") Long parentId) {

        return CommonResponse.successWithData(
                categoryService.getCategoryNameAndChildCategoriesByCategoryId(parentId)
        );
    }

    @PutMapping("/categories/update/new")
    public CommonResponse<Void> addCategory(@RequestBody CategoryAddRequest categoryAddRequest) {
        categoryService.addCategory(categoryAddRequest);
        return CommonResponse.success();
    }

    @PutMapping("/categories/update/{id}")
    public CommonResponse<Void> updateCategory(@RequestBody CategoryUpdateRequest categoryUpdateRequest,
                                               @PathVariable("id") Long categoryId) {

        categoryService.updateCategory(categoryUpdateRequest, categoryId);
        return CommonResponse.success();
    }

    @DeleteMapping("/categories/delete/{id}")
    public CommonResponse<Void> deleteCategory(@PathVariable("id") Long categoryId) {

        categoryService.deleteCategory(categoryId);
        return CommonResponse.success();
    }

}
