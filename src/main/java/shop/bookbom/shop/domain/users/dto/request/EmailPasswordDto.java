package shop.bookbom.shop.domain.users.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailPasswordDto {
    @Email
    String email;

    @NotBlank
    String password;
}
