package sosteam.throwapi.domain.user.controller.response;

import lombok.Data;

@Data
public class ReissueTokensResponse {
    private String accessToken;
    private String refreshToken;

    public ReissueTokensResponse(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
