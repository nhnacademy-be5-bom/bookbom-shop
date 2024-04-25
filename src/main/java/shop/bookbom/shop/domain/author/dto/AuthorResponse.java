package shop.bookbom.shop.domain.author.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthorResponse {
    private Long id;
    private String name;
    private String role;

    @Builder
    public AuthorResponse(Long id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }
}
