package shop.bookbom.shop.domain.users.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResetPasswordRequestDto {

    private Long id;
    private String password;
}
