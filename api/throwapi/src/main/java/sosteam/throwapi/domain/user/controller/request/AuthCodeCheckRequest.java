package sosteam.throwapi.domain.user.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthCodeCheckRequest {

    @NotNull
    @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$", message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotNull
    @Size(min = 6, max = 6, message = "확인 코드는 6자리 입니다.")
    private String checkCode;
}
