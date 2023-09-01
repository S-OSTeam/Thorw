package sosteam.throwapi.domain.user.entity.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sosteam.throwapi.domain.user.entity.SNSCategory;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReissueTokensDto {
    private String refreshToken;
}
