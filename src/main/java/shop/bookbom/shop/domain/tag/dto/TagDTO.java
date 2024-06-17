package shop.bookbom.shop.domain.tag.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.booktag.entity.BookTag;

/**
 * packageName    : shop.bookbom.shop.domain.tag.dto
 * fileName       : TagDTO
 * author         : UuLaptop
 * date           : 2024-04-13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-13        UuLaptop       최초 생성
 */
@Getter
@NoArgsConstructor
public class TagDTO {
    private Long id;
    private String name;

    @Builder
    @QueryProjection
    public TagDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static TagDTO from(BookTag bookTag) {
        return TagDTO.builder()
                .id(bookTag.getTag().getId())
                .name(bookTag.getTag().getName())
                .build();
    }
}
