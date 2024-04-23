package shop.bookbom.shop.domain.author.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : shop.bookbom.shop.domain.author.dto
 * fileName       : AuthorSimpleInfo
 * author         : UuLaptop
 * date           : 2024-04-17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-17        UuLaptop       최초 생성
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorSimpleInfo {
    private String role;
    private String name;

    @Builder
    public AuthorSimpleInfo(String role, String name) {
        this.role = role;
        this.name = name;
    }
}
