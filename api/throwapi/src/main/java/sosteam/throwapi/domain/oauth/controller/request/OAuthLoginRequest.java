package sosteam.throwapi.domain.oauth.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuthLoginRequest {
    @NotNull
    @Pattern(regexp = "^(NORMAL)|(KAKAO)|(GOOGLE)|(NAVER)", message = "sns 종류에 해당하지 않는 값이 입력 되었습니다.")
    private String sns;
    @NotNull
    private String accessToken;
}
