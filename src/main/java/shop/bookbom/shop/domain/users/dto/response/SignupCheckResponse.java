package shop.bookbom.shop.domain.users.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupCheckResponse {
    private boolean canUse;
}
