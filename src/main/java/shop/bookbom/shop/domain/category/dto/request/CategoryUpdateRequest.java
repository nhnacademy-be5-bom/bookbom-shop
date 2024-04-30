package shop.bookbom.shop.domain.category.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : shop.bookbom.shop.domain.category.dto.request
 * fileName       : CategoryUpdateRequest
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
public class CategoryUpdateRequest {
    Long id;
    String name;
    Long newParentId;
}
