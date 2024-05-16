package shop.bookbom.shop.domain.users.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SetPasswordRequest {
    @NotBlank(message = "현재 비밀번호를 입력하세요")
    String prePassword;

    @Pattern(regexp = "^((?=.*[*\\da-zA-Z])(?=.*[\\W]).{8,20})$", message = "비밀번호는 특수문자 포함 8자 이상이어야 합니다")
    @NotBlank(message = "신규 비밀번호를 입력하세요.")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력하세요.")
    private String confirmPassword;
}
