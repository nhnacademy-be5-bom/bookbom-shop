package shop.bookbom.shop.domain.users.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChangeRegisteredRequestDto {

    private Long id;
    private boolean registered;
}
