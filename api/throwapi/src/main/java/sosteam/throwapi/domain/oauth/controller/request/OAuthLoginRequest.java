package sosteam.throwapi.domain.oauth.controller.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuthLoginRequest {
    private String sns;
    private String accessToken;
    private String refreshToken;
}
