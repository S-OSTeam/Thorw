package sosteam.throwapi.domain.oauth.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sosteam.throwapi.global.entity.SNSCategory;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuthLoginDto {
    private SNSCategory sns;
    private String accessToken;
    private String refreshToken;
}
