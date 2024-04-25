package shop.bookbom.shop.domain.author.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.bookauthor.entity.BookAuthor;

/**
 * packageName    : shop.bookbom.shop.domain.author.dto
 * fileName       : AuthorDTO
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
public class AuthorDTO {
    private Long id;
    private String role;
    private String name;

    @Builder
    @QueryProjection
    public AuthorDTO(Long id, String role, String name) {
        this.id = id;
        this.role = role;
        this.name = name;
    }

    public static AuthorDTO from(BookAuthor bookAuthor) {
        return AuthorDTO.builder()
                .id(bookAuthor.getAuthor().getId())
                .role(bookAuthor.getRole())
                .name(bookAuthor.getAuthor().getName())
                .build();
    }

}
