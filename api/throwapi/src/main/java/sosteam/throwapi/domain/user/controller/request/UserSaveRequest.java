package sosteam.throwapi.domain.user.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveRequest {

    @NotNull
    @Pattern(regexp = "^[a-z]+[a-z0-9]{5,19}$", message = "올바른 ID 형식이 아닙니다.")
    private String inputId;

    @NotNull
    @Pattern(regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$", message = "올바른 비밀번호 형식이 아닙니다.")
    private String inputPassword;

    @Pattern(regexp = "^[0-9]+$", message = "올바른 snsId 형식이 아닙니다.")
    private String snsId;

    @Pattern(regexp = "^(NORMAL)|(KAKAO)|(GOOGLE)|(NAVER)", message = "sns 종류에 해당하지 않는 값이 입력 되었습니다.")
    private String sns;

    @NotNull
    @Pattern(regexp = "^(NORMAL)|(DORMANT)|(SIGNOUT)", message = "User 의 상태에 해당하지 않는 값이 입력 되었습니다.")
    private String userStatus;

    @NotNull
    @Pattern(regexp = "^(ROLE_GUEST)|(ROLE_USER)|(ROLE_ADMIN)", message = "User 의 역할(Role)에 해당하지 않는 값이 입력 되었습니다.")
    private String role;

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
