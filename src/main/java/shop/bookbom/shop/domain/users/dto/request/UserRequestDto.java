package shop.bookbom.shop.domain.users.dto.request;

import javax.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRequestDto {
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    private String password;

    private String roleName;
}
