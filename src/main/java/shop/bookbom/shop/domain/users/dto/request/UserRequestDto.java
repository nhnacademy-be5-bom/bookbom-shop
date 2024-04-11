package shop.bookbom.shop.domain.users.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRequestDto {
    private String email;

    private String password;

    private String roleName;
}
