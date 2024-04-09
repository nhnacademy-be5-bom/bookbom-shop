package shop.bookbom.shop.domain.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.category.dto.response.CategoryDepthResponse;
import shop.bookbom.shop.domain.category.service.CategoryService;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
@Slf4j
public class CategoryRestController {
    private final CategoryService categoryService;

    @GetMapping("/category/get/all")
    public CommonResponse<CategoryDepthResponse> getAllCategories() {
        return CommonResponse.successWithData(categoryService.getAllCategories());
    }

    @GetMapping("/category/get/depth1")
    public CommonResponse<CategoryDepthResponse> getDepthOneCategories() {
        return CommonResponse.successWithData(categoryService.getAllDepthOneCategories());
    }

    @GetMapping("/category/get/{parentId}")
    public CommonResponse<CategoryDepthResponse> getChildCategories(@PathVariable("parentId") Long parentId) {
        return CommonResponse.successWithData(categoryService.getChildCategoriesByCategoryId(parentId));
    }

}
