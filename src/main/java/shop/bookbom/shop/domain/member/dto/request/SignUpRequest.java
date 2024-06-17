package shop.bookbom.shop.domain.member.dto.request;

import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "아이디를 입력하세요.")
    @Email(message = "유효한 이메일 주소를 입력하세요.")
    private String email;
    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;
    @Size(min = 2, message = "이름은 최소 2자 이상이어야 합니다.")
    private String name;
    @NotEmpty(message = "닉네임을 입력해주세요.")
    private String nickname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "생년월일을 입력하세요.")
    private LocalDate birthDate;
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "휴대전화 양식으로 입력해주세요. ex)010-1234-5678")
    private String phoneNumber;
    @NotEmpty(message = "우편 번호를 입력해주세요.")
    private String addressNumber;
    @NotEmpty(message = "주소를 입력해주세요.")
    private String address;
    @NotEmpty(message = "상세주소를 입력해주세요.")
    private String addressDetail;
}
