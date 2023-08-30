package sosteam.throwapi.domain.user.controller.response;

import lombok.Data;

@Data
public class ReissueTokensResponse {
    private String sns;
    private String accessToken;
    private String refreshToken;

    public ReissueTokensResponse(String sns, String accessToken, String refreshToken){
        this.sns = sns;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
