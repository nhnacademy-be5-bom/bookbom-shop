package shop.bookbom.shop.domain.users.dto.response;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserIdRole {
    @NotNull
    Long userId;

    @NotBlank
    String role;
}
