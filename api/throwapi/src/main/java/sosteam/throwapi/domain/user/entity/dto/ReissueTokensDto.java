package sosteam.throwapi.domain.user.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sosteam.throwapi.global.entity.SNSCategory;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReissueTokensDto {
    private SNSCategory sns;
    private String accessToken;
    private String refreshToken;
}
