package sosteam.throwapi.domain.user.controller.request.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCngRequest {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "이름은 영어, 한글만 허용됩니다.")
    private String userName;

    @NotNull
    @Pattern(regexp = "^(010)[0-9]{8}$", message = "올바른 전화번호 형식이 아닙니다.")
    private String userPhoneNumber;

    @NotNull
    @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$", message = "올바른 이메일 형식이 아닙니다.")
    private String email;
}
