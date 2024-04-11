package shop.bookbom.shop.domain.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonListResponse;
import shop.bookbom.shop.domain.category.dto.CategoryDTO;
import shop.bookbom.shop.domain.category.service.CategoryService;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
@Slf4j
public class CategoryRestController {
    private final CategoryService categoryService;

//    @GetMapping("/category/get/all")
//    public CommonListResponse<CategoryDTO> getAllCategories() {
//        return CommonListResponse.successWithList(categoryService.getAllDepthOneCategories());
//    }

    @GetMapping("/category/get/depth1")
    public CommonListResponse<CategoryDTO> getDepthOneCategories() {
        return CommonListResponse.successWithList(categoryService.getAllDepthOneCategories());
    }

    @GetMapping("/category/get/{parentId}")
    @CrossOrigin(origins = "*")
    public CommonListResponse<CategoryDTO> getChildCategoriesOf(@PathVariable("parentId") Long parentId) {
        return CommonListResponse.successWithList(categoryService.getChildCategoriesByCategoryId(parentId));
    }

}
