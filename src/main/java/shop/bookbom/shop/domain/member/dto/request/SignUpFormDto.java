package shop.bookbom.shop.domain.member.dto.request;

import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignUpFormDto {
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotNull
    private LocalDate birthDate;

    @NotEmpty
    private String phoneNumber;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String addressNumber;

    @NotEmpty
    private String address;

    @NotEmpty
    private String addressDetail;
}
