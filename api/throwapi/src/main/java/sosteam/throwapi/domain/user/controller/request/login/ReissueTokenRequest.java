package sosteam.throwapi.domain.user.controller.request.login;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReissueTokenRequest {
    @NotNull
    @Pattern(regexp = "^(NORMAL)|(KAKAO)|(GOOGLE)|(NAVER)", message = "sns 종류에 해당하지 않는 값이 입력 되었습니다.")
    private String sns;
    @NotNull
    private String refreshToken;

}
