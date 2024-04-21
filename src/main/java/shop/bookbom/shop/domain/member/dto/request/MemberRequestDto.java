package shop.bookbom.shop.domain.member.dto.request;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberRequestDto {
    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String phoneNumber;

    @NotNull
    private LocalDate birthDate;

    @NotBlank
    private String nickname;
}
